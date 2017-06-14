package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.cards.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformations;
import it.polimi.ingsw.lim.common.network.rmi.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.board.ExcommunicationTile;
import it.polimi.ingsw.lim.server.game.cards.CardsHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.utils.CouncilPalaceReward;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		Utils.displayToLog("RMI Player logged in as: " + trimmedUsername);
		return this.finalizeAuthentication(trimmedUsername, roomType, serverSession);
	}

	@Override
	public AuthenticationInformations sendRegistration(String version, String username, String password, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException
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

	private AuthenticationInformations finalizeAuthentication(String username, RoomType roomType, IServerSession serverSession) throws RemoteException
	{
		List<DevelopmentCardInformations> developmentCardsInformations = new ArrayList<>();
		for (Period period : Period.values()) {
			for (DevelopmentCard developmentCard : CardsHandler.DEVELOPMENT_CARDS_BUILDING.getPeriods().get(period)) {
				developmentCardsInformations.add(developmentCard.getInformations());
			}
			for (DevelopmentCard developmentCard : CardsHandler.DEVELOPMENT_CARDS_CHARACTER.getPeriods().get(period)) {
				developmentCardsInformations.add(developmentCard.getInformations());
			}
			for (DevelopmentCard developmentCard : CardsHandler.DEVELOPMENT_CARDS_TERRITORY.getPeriods().get(period)) {
				developmentCardsInformations.add(developmentCard.getInformations());
			}
			for (DevelopmentCard developmentCard : CardsHandler.DEVELOPMENT_CARDS_VENTURE.getPeriods().get(period)) {
				developmentCardsInformations.add(developmentCard.getInformations());
			}
		}
		List<LeaderCardInformations> leaderCardsInformations = new ArrayList<>();
		for (LeaderCard leaderCard : CardsHandler.getLeaderCards()) {
			leaderCardsInformations.add(leaderCard.getInformations());
		}
		List<ExcommunicationTileInformations> excommunicationTilesInformations = new ArrayList<>();
		for (ExcommunicationTile excommunicationTile : ExcommunicationTile.values()) {
			excommunicationTilesInformations.add(new ExcommunicationTileInformations(excommunicationTile.getIndex(), excommunicationTile.getTexturePath(), excommunicationTile.getModifier().getDescription()));
		}
		List<CouncilPalaceRewardInformations> councilPalaceRewardsInformations = new ArrayList<>();
		for (CouncilPalaceReward councilPalaceReward : BoardHandler.getCouncilPrivilegeRewards()) {
			councilPalaceRewardsInformations.add(councilPalaceReward.getInformations());
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
			return new AuthenticationInformations(developmentCardsInformations, leaderCardsInformations, excommunicationTilesInformations, councilPalaceRewardsInformations, clientSession, new RoomInformations(targetRoom.getRoomType(), playerUsernames));
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
			return new AuthenticationInformations(developmentCardsInformations, leaderCardsInformations, excommunicationTilesInformations, councilPalaceRewardsInformations, clientSession, new RoomInformations(playerRoom.getRoomType(), playerUsernames));
		}
	}

	List<ClientSession> getClientSessions()
	{
		return this.clientSessions;
	}
}
