package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.rmi.AuthenticationInformationsGameRMI;
import it.polimi.ingsw.lim.common.network.rmi.AuthenticationInformationsLobbyRMI;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
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
		AuthenticationInformations authenticationInformations = Utils.fillAuthenticationInformations();
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
			AuthenticationInformationsLobbyRMI authenticationInformationsLobby = new AuthenticationInformationsLobbyRMI(authenticationInformations, clientSession);
			authenticationInformationsLobby.setGameStarted(false);
			authenticationInformationsLobby.setRoomInformations(new RoomInformations(targetRoom.getRoomType(), playerUsernames));
			return authenticationInformationsLobby;
		} else {
			ConnectionRMI connectionRmi = null;
			for (Connection connection : playerRoom.getPlayers()) {
				if (connection.getUsername().equals(username)) {
					connectionRmi = new ConnectionRMI(username, serverSession, connection.getPlayer());
					connectionRmi.getPlayer().setConnection(connectionRmi);
					connectionRmi.getPlayer().setOnline(true);
					playerRoom.getPlayers().set(playerRoom.getPlayers().indexOf(connection), connectionRmi);
					playerRoom.getPlayers().remove(connection);
					break;
				}
			}
			if (connectionRmi == null) {
				throw new AuthenticationFailedException("Server error.");
			}
			ClientSession clientSession = new ClientSession(connectionRmi);
			this.clientSessions.add(clientSession);
			Server.getInstance().getConnections().add(connectionRmi);
			AuthenticationInformationsGameRMI authenticationInformationsGame = new AuthenticationInformationsGameRMI(authenticationInformations, clientSession);
			authenticationInformationsGame.setGameStarted(true);
			authenticationInformationsGame.setExcommunicationTiles(playerRoom.getGameHandler().getBoardHandler().getMatchExcommunicationTilesIndexes());
			authenticationInformationsGame.setCouncilPrivilegeRewards(playerRoom.getGameHandler().getBoardHandler().getMatchCouncilPrivilegeRewards());
			authenticationInformationsGame.setPlayersIdentifications(playerRoom.getGameHandler().getPlayersIdentifications());
			authenticationInformationsGame.setOwnPlayerIndex(connectionRmi.getPlayer().getIndex());
			if (playerRoom.getGameHandler().getCurrentPeriod() != null && playerRoom.getGameHandler().getCurrentRound() != null) {
				authenticationInformationsGame.setGameInitialized(true);
				authenticationInformationsGame.setGameInformations(playerRoom.getGameHandler().generateGameInformations());
				authenticationInformationsGame.setPlayersInformations(playerRoom.getGameHandler().generatePlayersInformations());
				authenticationInformationsGame.setOwnLeaderCardsHand(playerRoom.getGameHandler().generateLeaderCardsHand(connectionRmi.getPlayer()));
				authenticationInformationsGame.setTurnPlayerIndex(playerRoom.getGameHandler().getTurnPlayer().getIndex());
				if (playerRoom.getGameHandler().getTurnPlayer().getIndex() != connectionRmi.getPlayer().getIndex()) {
					authenticationInformationsGame.setAvailableActions(playerRoom.getGameHandler().generateAvailableActions(connectionRmi.getPlayer()));
				}
			} else {
				authenticationInformationsGame.setGameInitialized(false);
			}
			return authenticationInformationsGame;
		}
	}

	List<ClientSession> getClientSessions()
	{
		return this.clientSessions;
	}
}
