package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.AuthenticationInformations;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
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
		ConnectionRMI connectionRmi = new ConnectionRMI(Server.getInstance().getConnectionId(), username, serverSession);
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
			targetRoom = new Room(Server.getInstance().getRoomId(), roomType);
			Server.getInstance().getRooms().add(targetRoom);
		}
		targetRoom.addPlayer(connectionRmi);
		List<String> playerUsernames = new ArrayList<>();
		for (Connection player : targetRoom.getPlayers()) {
			player.sendRoomEntryOther(username);
			playerUsernames.add(player.getUsername());
		}
		return new AuthenticationInformations(clientSession, new RoomInformations(targetRoom.getId(), targetRoom.getRoomType(), playerUsernames));
	}

	List<ClientSession> getClientSessions()
	{
		return this.clientSessions;
	}
}
