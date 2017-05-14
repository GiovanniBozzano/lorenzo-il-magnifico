package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.Room;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Connection implements IConnection
{
	private final int id;
	private String username;
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();

	protected Connection(int id)
	{
		this.id = id;
	}

	protected Connection(int id, String username)
	{
		this.id = id;
		this.username = username;
	}

	static void disconnectAll()
	{
		for (Connection connection : Server.getInstance().getConnections()) {
			connection.disconnect(true, null);
		}
		Server.getInstance().getConnections().clear();
	}

	private static void broadcastRoomsUpdate()
	{
		for (Connection connection : Utils.getPlayersInLobby()) {
			connection.sendRoomList(Utils.convertRoomsToInformations());
		}
	}

	public static void broadcastChatMessage(String text)
	{
		for (Connection connection : Utils.getPlayersInRooms()) {
			connection.sendChatMessage(text);
		}
	}

	@Override
	public void disconnect(boolean flag, String message)
	{
		this.heartbeat.shutdownNow();
		Server.getInstance().getConnections().remove(this);
		for (Room room : Server.getInstance().getRooms()) {
			room.getPlayers().remove(this);
			if (room.getPlayers().isEmpty()) {
				Server.getInstance().getRooms().remove(room);
			} else {
				for (Connection connection : room.getPlayers()) {
					connection.sendRoomExitOther(this.getUsername());
				}
			}
		}
		Connection.broadcastRoomsUpdate();
	}

	@Override
	public void handleRequestRoomList()
	{
		this.sendRoomList(Utils.convertRoomsToInformations());
	}

	@Override
	public void handleRoomCreation(String name)
	{
		String trimmedName = name.replaceAll("^\\s+|\\s+$", "");
		if (!trimmedName.matches("^[\\w\\-\\s]{1,16}$")) {
			this.disconnect(true, null);
			return;
		}
		for (Room room : Server.getInstance().getRooms()) {
			if (room.getName().equals(trimmedName)) {
				this.sendRoomCreationFailure();
				return;
			}
		}
		int roomId = Server.getInstance().getRoomId();
		Room room = new Room(roomId, trimmedName);
		room.getPlayers().add(this);
		Server.getInstance().getRooms().add(room);
		List<String> playerNames = new ArrayList<>();
		playerNames.add(this.username);
		this.sendRoomEntryConfirmation(new RoomInformations(roomId, trimmedName, playerNames));
		Connection.broadcastRoomsUpdate();
	}

	@Override
	public void handleRoomEntry(int id)
	{
		if (Utils.getPlayersInRooms().contains(this)) {
			this.disconnect(true, null);
			return;
		}
		Room targetRoom = null;
		for (Room room : Server.getInstance().getRooms()) {
			if (room.getId() == id) {
				targetRoom = room;
				break;
			}
		}
		if (targetRoom == null || targetRoom.getPlayers().size() > 3) {
			this.disconnect(true, null);
			return;
		}
		targetRoom.getPlayers().add(this);
		List<String> playerNames = new ArrayList<>();
		for (Connection connection : targetRoom.getPlayers()) {
			playerNames.add(connection.getUsername());
		}
		this.sendRoomEntryConfirmation(new RoomInformations(targetRoom.getId(), targetRoom.getName(), playerNames));
		for (Connection connection : targetRoom.getPlayers()) {
			connection.sendRoomEntryOther(this.username);
		}
		Connection.broadcastRoomsUpdate();
	}

	@Override
	public void handleRoomExit()
	{
		for (Room room : Server.getInstance().getRooms()) {
			if (room.getPlayers().contains(this)) {
				room.getPlayers().remove(this);
				if (room.getPlayers().isEmpty()) {
					Server.getInstance().getRooms().remove(room);
				} else {
					for (Connection connection : room.getPlayers()) {
						connection.sendRoomExitOther(this.username);
					}
				}
				break;
			}
		}
		Connection.broadcastRoomsUpdate();
	}

	@Override
	public void handleChatMessage(String text)
	{
		for (Connection otherConnection : Utils.getPlayersInRooms()) {
			if (otherConnection != this) {
				otherConnection.sendChatMessage("[" + this.getUsername() + "]: " + text);
			}
		}
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public String getUsername()
	{
		return this.username;
	}

	@Override
	public void setUsername(String username)
	{
		this.username = username;
	}

	@Override
	public ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}
}
