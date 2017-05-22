package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketAuthentication;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketLogin;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRegistration;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private static final Map<PacketType, IPacketHandler> PACKET_HANDLERS = new EnumMap<>(PacketType.class);

	static {
		PacketListener.PACKET_HANDLERS.put(PacketType.HEARTBEAT, (connectionSocket, packet) -> {
			// This method is empty because it is only called to check the connection.
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_TIMER_REQUEST, (connectionSocket, packet) -> connectionSocket.handleRoomTimerRequest());
		PacketListener.PACKET_HANDLERS.put(PacketType.CHAT_MESSAGE, (connectionSocket, packet) -> connectionSocket.handleChatMessage(((PacketChatMessage) packet).getText()));
	}

	private final ConnectionSocket connectionSocket;
	private volatile boolean keepGoing = true;

	PacketListener(ConnectionSocket connectionSocket)
	{
		this.connectionSocket = connectionSocket;
	}

	@Override
	public void run()
	{
		if (!this.waitAuthentication()) {
			return;
		}
		while (this.keepGoing) {
			Packet packet;
			try {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				Server.getLogger().log(Level.INFO, "Socket Client " + this.connectionSocket.getId() + (this.connectionSocket.getUsername() != null ? " : " + this.connectionSocket.getUsername() : "") + " disconnected.", exception);
				if (!this.keepGoing) {
					return;
				}
				this.connectionSocket.disconnect(false, null);
				return;
			}
			if (packet == null) {
				this.connectionSocket.disconnect(false, null);
				return;
			}
			PacketListener.PACKET_HANDLERS.get(packet.getPacketType()).execute(this.connectionSocket, packet);
		}
	}

	private boolean waitAuthentication()
	{
		while (this.connectionSocket.getUsername() == null) {
			Packet packet;
			try {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				Server.getLogger().log(Level.INFO, "Socket Client disconnected.", exception);
				if (!this.keepGoing) {
					return false;
				}
				this.connectionSocket.disconnect(false, null);
				return false;
			}
			if (packet.getPacketType() != PacketType.LOGIN && packet.getPacketType() != PacketType.REGISTRATION) {
				continue;
			}
			String trimmedUsername = ((PacketAuthentication) packet).getUsername().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
			try {
				if (packet.getPacketType() == PacketType.LOGIN) {
					Utils.checkLogin(((PacketLogin) packet).getVersion(), trimmedUsername, ((PacketLogin) packet).getPassword());
					Utils.displayToLog("Socket Player logged in as: " + trimmedUsername);
				} else if (packet.getPacketType() == PacketType.REGISTRATION) {
					Utils.checkRegistration(((PacketRegistration) packet).getVersion(), trimmedUsername, ((PacketRegistration) packet).getPassword());
					Utils.displayToLog("Socket Player registerd as: " + trimmedUsername);
				}
			} catch (AuthenticationFailedException exception) {
				Server.getLogger().log(Level.INFO, "Socket Client failed authentication.", exception);
				this.connectionSocket.sendAuthenticationFailure(exception.getLocalizedMessage());
				return false;
			}
			this.connectionSocket.setUsername(trimmedUsername);
			Room targetRoom = null;
			for (Room room : Server.getInstance().getRooms()) {
				if (!room.getIsStarted() && room.getRoomType() == ((PacketAuthentication) packet).getRoomType() && room.getPlayers().size() < ((PacketAuthentication) packet).getRoomType().getPlayersNumber()) {
					targetRoom = room;
					break;
				}
			}
			if (targetRoom == null) {
				targetRoom = new Room(Server.getInstance().getRoomId(), ((PacketAuthentication) packet).getRoomType());
				Server.getInstance().getRooms().add(targetRoom);
			}
			targetRoom.addPlayer(this.connectionSocket);
			List<String> playerUsernames = new ArrayList<>();
			for (Connection player : targetRoom.getPlayers()) {
				player.sendRoomEntryOther(trimmedUsername);
				playerUsernames.add(player.getUsername());
			}
			this.connectionSocket.sendAuthenticationConfirmation(new RoomInformations(targetRoom.getId(), targetRoom.getRoomType(), playerUsernames));
		}
		return true;
	}

	synchronized void end()
	{
		this.keepGoing = false;
	}
}
