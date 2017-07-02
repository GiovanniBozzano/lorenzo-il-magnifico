package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.rmi.AuthenticationInformationsGameRMI;
import it.polimi.ingsw.lim.common.network.rmi.AuthenticationInformationsLobbyRMI;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.ExcommunicationTile;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.*;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Authentication extends UnicastRemoteObject implements IAuthentication
{
	private final transient List<ClientSession> clientSessions = new ArrayList<>();

	public Authentication() throws RemoteException
	{
		super();
	}

	@Override
	public AuthenticationInformations sendLogin(String version, String username, String password, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException
	{
		String trimmedUsername = username.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		Utils.checkLogin(version, trimmedUsername, password);
		Server.getInstance().getInterfaceHandler().displayToLog("RMI Player logged in as: " + trimmedUsername);
		return this.finalizeAuthentication(trimmedUsername, roomType, serverSession);
	}

	@Override
	public AuthenticationInformations sendRegistration(String version, String username, String password, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException
	{
		String trimmedUsername = username.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		Utils.checkRegistration(version, trimmedUsername, password);
		Server.getInstance().getInterfaceHandler().displayToLog("RMI Player registerd as: " + trimmedUsername);
		return this.finalizeAuthentication(trimmedUsername, roomType, serverSession);
	}

	@Override
	public void sendHeartbeat() throws RemoteException
	{
		// This method is empty because it is only called to check the connection.
	}

	@Override
	public boolean equals(Object object)
	{
		if (this == object) {
			return true;
		}
		if (object == null || this.getClass() != object.getClass()) {
			return false;
		}
		if (!super.equals(object)) {
			return false;
		}
		Authentication that = (Authentication) object;
		return Objects.equals(this.clientSessions, that.clientSessions);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.clientSessions);
	}

	private AuthenticationInformations finalizeAuthentication(String username, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException
	{
		Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuildingsInformations = new HashMap<>();
		Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacterInformations = new HashMap<>();
		Map<Integer, DevelopmentCardTerritoryInformations> developmentsCardTerritoryInformations = new HashMap<>();
		Map<Integer, DevelopmentCardVentureInformations> developmentCardsVentureInformations = new HashMap<>();
		for (Period period : Period.values()) {
			for (DevelopmentCardBuilding developmentCardBuilding : CardsHandler.getDevelopmentCardsBuilding().get(period)) {
				developmentCardsBuildingsInformations.put(developmentCardBuilding.getIndex(), developmentCardBuilding.getInformations());
			}
			for (DevelopmentCardCharacter developmentCardCharacter : CardsHandler.getDevelopmentCardsCharacter().get(period)) {
				developmentCardsCharacterInformations.put(developmentCardCharacter.getIndex(), developmentCardCharacter.getInformations());
			}
			for (DevelopmentCardTerritory developmentCardTerritory : CardsHandler.getDevelopmentCardsTerritory().get(period)) {
				developmentsCardTerritoryInformations.put(developmentCardTerritory.getIndex(), developmentCardTerritory.getInformations());
			}
			for (DevelopmentCardVenture developmentCardVenture : CardsHandler.getDevelopmentCardsVenture().get(period)) {
				developmentCardsVentureInformations.put(developmentCardVenture.getIndex(), developmentCardVenture.getInformations());
			}
		}
		Map<Integer, LeaderCardInformations> leaderCardsInformations = new HashMap<>();
		for (LeaderCard leaderCard : CardsHandler.getLeaderCards()) {
			leaderCardsInformations.put(leaderCard.getIndex(), leaderCard.getInformations());
		}
		Map<Integer, ExcommunicationTileInformations> excommunicationTilesInformations = new HashMap<>();
		for (ExcommunicationTile excommunicationTile : ExcommunicationTile.values()) {
			excommunicationTilesInformations.put(excommunicationTile.getIndex(), new ExcommunicationTileInformations(excommunicationTile.getTexturePath(), excommunicationTile.getModifier().getDescription()));
		}
		Map<Integer, PersonalBonusTileInformations> personalBonusTilesInformations = new HashMap<>();
		for (PersonalBonusTile personalBonusTile : PersonalBonusTile.values()) {
			personalBonusTilesInformations.put(personalBonusTile.getIndex(), new PersonalBonusTileInformations(personalBonusTile.getTexturePath(), personalBonusTile.getPlayerBoardTexturePath(), personalBonusTile.getProductionActivationCost(), personalBonusTile.getProductionInstantResources(), personalBonusTile.getHarvestActivationCost(), personalBonusTile.getHarvestInstantResources()));
		}
		Room playerRoom = Room.getPlayerRoom(username);
		if (playerRoom == null || playerRoom.isEndGame()) {
			ConnectionRMI connectionRmi = new ConnectionRMI(username, serverSession);
			ClientSession clientSession = new ClientSession(connectionRmi);
			this.clientSessions.add(clientSession);
			Server.getInstance().getConnections().add(connectionRmi);
			Room targetRoom = null;
			for (Room room : Server.getInstance().getRooms()) {
				if (room.getGameHandler() == null && room.getRoomType() == roomType && room.getPlayers().size() < roomType.getPlayersNumber()) {
					targetRoom = room;
					break;
				}
			}
			if (targetRoom == null) {
				targetRoom = new Room(roomType);
				Server.getInstance().getRooms().add(targetRoom);
			}
			targetRoom.addPlayer(connectionRmi);
			List<String> playerUsernames = new ArrayList<>();
			for (Connection player : targetRoom.getPlayers()) {
				if (player != connectionRmi) {
					player.sendRoomEntryOther(username);
				}
				playerUsernames.add(player.getUsername());
			}
			AuthenticationInformationsLobbyRMI authenticationInformations = new AuthenticationInformationsLobbyRMI();
			authenticationInformations.setDevelopmentCardsBuildingInformations(developmentCardsBuildingsInformations);
			authenticationInformations.setDevelopmentCardsCharacterInformations(developmentCardsCharacterInformations);
			authenticationInformations.setDevelopmentCardsTerritoryInformations(developmentsCardTerritoryInformations);
			authenticationInformations.setDevelopmentCardsVentureInformations(developmentCardsVentureInformations);
			authenticationInformations.setLeaderCardsInformations(leaderCardsInformations);
			authenticationInformations.setExcommunicationTilesInformations(excommunicationTilesInformations);
			authenticationInformations.setPersonalBonusTilesInformations(personalBonusTilesInformations);
			authenticationInformations.setGameStarted(false);
			authenticationInformations.setRoomInformations(new RoomInformations(targetRoom.getRoomType(), playerUsernames));
			authenticationInformations.setClientSession(clientSession);
			return authenticationInformations;
		} else {
			ConnectionRMI connectionRmi = null;
			for (Connection player : playerRoom.getPlayers()) {
				if (player.getUsername().equals(username)) {
					connectionRmi = new ConnectionRMI(username, serverSession, player.getPlayer());
					connectionRmi.getPlayer().setConnection(connectionRmi);
					connectionRmi.getPlayer().setOnline(true);
					playerRoom.getPlayers().set(playerRoom.getPlayers().indexOf(player), connectionRmi);
					playerRoom.getPlayers().remove(player);
					break;
				}
			}
			if (connectionRmi == null) {
				throw new AuthenticationFailedException("Server error.");
			}
			ClientSession clientSession = new ClientSession(connectionRmi);
			this.clientSessions.add(clientSession);
			Server.getInstance().getConnections().add(connectionRmi);
			AuthenticationInformationsGameRMI authenticationInformations = new AuthenticationInformationsGameRMI();
			authenticationInformations.setDevelopmentCardsBuildingInformations(developmentCardsBuildingsInformations);
			authenticationInformations.setDevelopmentCardsCharacterInformations(developmentCardsCharacterInformations);
			authenticationInformations.setDevelopmentCardsTerritoryInformations(developmentsCardTerritoryInformations);
			authenticationInformations.setDevelopmentCardsVentureInformations(developmentCardsVentureInformations);
			authenticationInformations.setLeaderCardsInformations(leaderCardsInformations);
			authenticationInformations.setExcommunicationTilesInformations(excommunicationTilesInformations);
			authenticationInformations.setPersonalBonusTilesInformations(personalBonusTilesInformations);
			authenticationInformations.setGameStarted(true);
			authenticationInformations.setExcommunicationTiles(playerRoom.getGameHandler().getBoardHandler().getMatchExcommunicationTilesIndexes());
			authenticationInformations.setCouncilPrivilegeRewards(playerRoom.getGameHandler().getBoardHandler().getMatchCouncilPrivilegeRewards());
			authenticationInformations.setPlayersIdentifications(playerRoom.getGameHandler().getPlayersIdentifications());
			authenticationInformations.setOwnPlayerIndex(connectionRmi.getPlayer().getIndex());
			if (playerRoom.getGameHandler().getCurrentPeriod() != null && playerRoom.getGameHandler().getCurrentRound() != null) {
				authenticationInformations.setGameInitialized(true);
				authenticationInformations.setGameInformations(playerRoom.getGameHandler().generateGameInformations());
				authenticationInformations.setPlayersInformations(playerRoom.getGameHandler().generatePlayersInformations());
				authenticationInformations.setOwnLeaderCardsHand(playerRoom.getGameHandler().generateLeaderCardsHand(connectionRmi.getPlayer()));
				authenticationInformations.setTurnPlayerIndex(playerRoom.getGameHandler().getTurnPlayer().getIndex());
				if (playerRoom.getGameHandler().getTurnPlayer().getIndex() != connectionRmi.getPlayer().getIndex()) {
					authenticationInformations.setAvailableActions(playerRoom.getGameHandler().generateAvailableActions(connectionRmi.getPlayer()));
				}
			} else {
				authenticationInformations.setGameInitialized(false);
			}
			authenticationInformations.setClientSession(clientSession);
			return authenticationInformations;
		}
	}

	List<ClientSession> getClientSessions()
	{
		return this.clientSessions;
	}
}
