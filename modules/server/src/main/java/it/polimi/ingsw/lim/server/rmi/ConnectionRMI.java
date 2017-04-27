package it.polimi.ingsw.lim.server.rmi;

import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.IConnection;
import it.polimi.ingsw.lim.server.Room;
import it.polimi.ingsw.lim.server.Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConnectionRMI implements IConnection
{
	private final int id;
	private final String name;
	private final IServerSession serverSession;

	ConnectionRMI(int id, String name, IServerSession serverSession)
	{
		this.id = id;
		this.name = name;
		this.serverSession = serverSession;
	}

	@Override
	public void disconnect()
	{
		Server.getInstance().getConnections().remove(this);
		try {
			this.serverSession.disconnect();
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendRoomList()
	{
		List<RoomInformations> rooms = new ArrayList<>();
		for (Room room : Server.getInstance().getRooms().values()) {
			List<String> playerNames = new ArrayList<>();
			for (IConnection player : room.getPlayers()) {
				playerNames.add(player.getName());
			}
			rooms.add(new RoomInformations(room.getId(), room.getName(), playerNames));
		}
		try {
			this.serverSession.sendRoomList(rooms);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendLogMessage(String text)
	{
		try {
			this.serverSession.sendLogMessage(text);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendChatMessage(String text)
	{
		try {
			this.serverSession.sendChatMessage(text);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void handleRequestRoomList()
	{
		this.sendRoomList();
	}

	@Override
	public void handleRoomEntry(int id)
	{
		if (Server.getInstance().getRooms().get(id) == null || Server.getInstance().getRooms().get(id).getPlayers().contains(this) || Server.getInstance().getRooms().get(id).getPlayers().size() >= 4) {
			try {
				this.serverSession.disconnect();
			} catch (RemoteException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		Server.getInstance().getRooms().get(id).getPlayers().add(this);
		Server.getInstance().broadcastRoomsUpdate();
	}

	@Override
	public void handleRoomExit(int id)
	{
		if (Server.getInstance().getRooms().get(id) == null || !Server.getInstance().getRooms().get(id).getPlayers().contains(this)) {
			try {
				this.serverSession.disconnect();
			} catch (RemoteException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		Server.getInstance().getRooms().get(id).getPlayers().remove(this);
		if (Server.getInstance().getRooms().get(id).getPlayers().isEmpty()) {
			Server.getInstance().getRooms().remove(id);
		}
		Server.getInstance().broadcastRoomsUpdate();
	}

	@Override
	public void handleChatMessage(String text)
	{
		for (IConnection otherConnection : Server.getInstance().getConnections()) {
			if (otherConnection != this) {
				otherConnection.sendChatMessage(this.name + ": " + text);
			}
		}
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	public IServerSession getServerSession()
	{
		return this.serverSession;
	}
}
