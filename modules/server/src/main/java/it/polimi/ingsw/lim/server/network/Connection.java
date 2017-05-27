package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.player.PlayerInformations;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Connection
{
	private final int id;
	private String username;
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();
	private PlayerInformations playerInformations;

	protected Connection(int id)
	{
		this.id = id;
	}

	protected Connection(int id, String username)
	{
		this.id = id;
		this.username = username;
	}

	public static void disconnectAll()
	{
		for (Connection connection : Server.getInstance().getConnections()) {
			connection.disconnect(true, null);
		}
		Server.getInstance().getConnections().clear();
	}

	public static void broadcastChatMessage(String text)
	{
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getUsername() == null) {
				continue;
			}
			connection.sendChatMessage(text);
		}
	}

	public void disconnect(boolean flag, String message)
	{
		this.heartbeat.shutdownNow();
		Server.getInstance().getConnections().remove(this);
		for (Room room : Server.getInstance().getRooms()) {
			room.removePlayer(this);
			if (room.getPlayers().isEmpty()) {
				Server.getInstance().getRooms().remove(room);
			} else {
				for (Connection connection : room.getPlayers()) {
					connection.sendRoomExitOther(this.username);
				}
			}
		}
	}

	public abstract void sendHeartbeat();

	public abstract void sendRoomEntryOther(String name);

	public abstract void sendRoomExitOther(String name);

	public abstract void sendRoomTimer(int timer);

	public abstract void sendGameStarted(RoomInformations roomInformations);

	public abstract void sendLogMessage(String text);

	public abstract void sendChatMessage(String text);

	public void handleRoomTimerRequest()
	{
		Room room = Utils.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		this.sendRoomTimer(room.getTimer());
	}

	public void handleChatMessage(String text)
	{
		Room room = Utils.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		for (Connection otherConnection : room.getPlayers()) {
			if (otherConnection != this) {
				otherConnection.sendChatMessage("[" + this.getUsername() + "]: " + text);
			}
		}
	}

	public int getId()
	{
		return this.id;
	}

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	protected ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}

	public PlayerInformations getPlayerInformations()
	{
		return this.playerInformations;
	}
}
