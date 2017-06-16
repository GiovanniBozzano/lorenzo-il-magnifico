package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.network.rmi.AuthenticationInformationsRMI;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.board.ExcommunicationTile;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.*;
import it.polimi.ingsw.lim.server.game.utils.CouncilPalaceReward;
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
	public AuthenticationInformationsRMI sendLogin(String version, String username, String password, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException
	{
		String trimmedUsername = username.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		Utils.checkLogin(version, trimmedUsername, password);
		Utils.displayToLog("RMI Player logged in as: " + trimmedUsername);
		return this.finalizeAuthentication(trimmedUsername, roomType, serverSession);
	}

	@Override
	public AuthenticationInformationsRMI sendRegistration(String version, String username, String password, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException
	{
		String trimmedUsername = username.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		Utils.checkRegistration(version, trimmedUsername, password);
		Utils.displayToLog("RMI Player registerd as: " + trimmedUsername);
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

	private AuthenticationInformationsRMI finalizeAuthentication(String username, RoomType roomType, IServerSession serverSession) throws RemoteException
	{
		Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuildingsInformations = new HashMap<>();
		Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacterInformations = new HashMap<>();
		Map<Integer, DevelopmentCardTerritoryInformations> developmentsCardTerritoryInformations = new HashMap<>();
		Map<Integer, DevelopmentCardVentureInformations> developmentCardsVentureInformations = new HashMap<>();
		for (Period period : Period.values()) {
			for (DevelopmentCardBuilding developmentCardBuilding : CardsHandler.DEVELOPMENT_CARDS_BUILDING.get(period)) {
				developmentCardsBuildingsInformations.put(developmentCardBuilding.getIndex(), developmentCardBuilding.getInformations());
			}
			for (DevelopmentCardCharacter developmentCardCharacter : CardsHandler.DEVELOPMENT_CARDS_CHARACTER.get(period)) {
				developmentCardsCharacterInformations.put(developmentCardCharacter.getIndex(), developmentCardCharacter.getInformations());
			}
			for (DevelopmentCardTerritory developmentCardTerritory : CardsHandler.DEVELOPMENT_CARDS_TERRITORY.get(period)) {
				developmentsCardTerritoryInformations.put(developmentCardTerritory.getIndex(), developmentCardTerritory.getInformations());
			}
			for (DevelopmentCardVenture developmentCardVenture : CardsHandler.DEVELOPMENT_CARDS_VENTURE.get(period)) {
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
		Map<Integer, CouncilPalaceRewardInformations> councilPalaceRewardsInformations = new HashMap<>();
		for (CouncilPalaceReward councilPalaceReward : BoardHandler.getCouncilPrivilegeRewards()) {
			councilPalaceRewardsInformations.put(councilPalaceReward.getIndex(), councilPalaceReward.getInformations());
		}
		Map<Integer, PersonalBonusTileInformations> personalBonusTilesInformations = new HashMap<>();
		for (PersonalBonusTile personalBonusTile : PersonalBonusTile.values()) {
			personalBonusTilesInformations.put(personalBonusTile.getIndex(), new PersonalBonusTileInformations(personalBonusTile.getTexturePath(), personalBonusTile.getProductionActivationCost(), personalBonusTile.getProductionInstantResources(), personalBonusTile.getHarvestActivationCost(), personalBonusTile.getHarvestInstantResources()));
		}
		Room playerRoom;
		if ((playerRoom = Room.getPlayerRoom(username)) == null) {
			ConnectionRMI connectionRmi = new ConnectionRMI(username, serverSession);
			ClientSession clientSession = new ClientSession(connectionRmi);
			this.clientSessions.add(clientSession);
			Server.getInstance().getConnections().add(connectionRmi);
			Room targetRoom = null;
			for (Room room : Server.getInstance().getRooms()) {
				if (!room.getIsStarted() && room.getRoomType() == roomType && room.getPlayers().size() < roomType.getPlayersNumber()) {
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
				player.sendRoomEntryOther(username);
				playerUsernames.add(player.getUsername());
			}
			return new AuthenticationInformationsRMI(developmentCardsBuildingsInformations, developmentCardsCharacterInformations, developmentsCardTerritoryInformations, developmentCardsVentureInformations, leaderCardsInformations, excommunicationTilesInformations, councilPalaceRewardsInformations, personalBonusTilesInformations, new RoomInformations(targetRoom.getRoomType(), playerUsernames), clientSession);
		} else {
			ConnectionRMI connectionRmi = null;
			for (Connection player : playerRoom.getPlayers()) {
				if (player.getUsername().equals(username)) {
					player.getPlayerHandler().setOnline(true);
					connectionRmi = new ConnectionRMI(username, serverSession, player.getPlayerHandler());
					playerRoom.getPlayers().set(playerRoom.getPlayers().indexOf(player), connectionRmi);
					playerRoom.getPlayers().remove(player);
					break;
				}
			}
			ClientSession clientSession = new ClientSession(connectionRmi);
			this.clientSessions.add(clientSession);
			Server.getInstance().getConnections().add(connectionRmi);
			// TODO aggiorno il giocatore
			List<String> playerUsernames = new ArrayList<>();
			for (Connection player : playerRoom.getPlayers()) {
				playerUsernames.add(player.getUsername());
			}
			return new AuthenticationInformationsRMI(developmentCardsBuildingsInformations, developmentCardsCharacterInformations, developmentsCardTerritoryInformations, developmentCardsVentureInformations, leaderCardsInformations, excommunicationTilesInformations, councilPalaceRewardsInformations, personalBonusTilesInformations, new RoomInformations(playerRoom.getRoomType(), playerUsernames), clientSession);
		}
	}

	List<ClientSession> getClientSessions()
	{
		return this.clientSessions;
	}
}
