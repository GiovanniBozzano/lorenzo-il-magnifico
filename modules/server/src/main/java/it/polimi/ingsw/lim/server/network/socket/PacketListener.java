package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketLogin;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRegistration;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomCreation;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomEntry;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private static final Map<PacketType, IPacketHandler> PACKET_HANDLERS = new HashMap<>();

	static {
		PacketListener.PACKET_HANDLERS.put(PacketType.CHAT_MESSAGE, (connectionSocket, packet) -> connectionSocket.handleChatMessage(((PacketChatMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_CREATION, (connectionSocket, packet) -> connectionSocket.handleRoomCreation(((PacketRoomCreation) packet).getName()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_ENTRY, (connectionSocket, packet) -> connectionSocket.handleRoomEntry(((PacketRoomEntry) packet).getId()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_EXIT, (connectionSocket, packet) -> connectionSocket.handleRoomExit());
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_LIST_REQUEST, (connectionSocket, packet) -> connectionSocket.handleRoomListRequest());
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
		this.connectionSocket.sendAuthenticationConfirmation();
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
				Server.getLogger().log(Level.INFO, "Socket Client " + this.connectionSocket.getSocket().getInetAddress().getHostAddress() + " : " + this.connectionSocket.getId() + " disconnected.", exception);
				if (!this.keepGoing) {
					return false;
				}
				this.connectionSocket.disconnect(false, null);
				return false;
			}
			try {
				if (packet.getPacketType() == PacketType.LOGIN) {
					String trimmedUsername = ((PacketLogin) packet).getUsername().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
					Utils.checkLogin(((PacketLogin) packet).getVersion(), trimmedUsername, ((PacketLogin) packet).getPassword());
					this.connectionSocket.setUsername(trimmedUsername);
					Utils.displayToLog("Socket Player " + this.connectionSocket.getSocket().getInetAddress().getHostAddress() + " : " + this.connectionSocket.getId() + " logged in as: " + trimmedUsername);
				} else if (packet.getPacketType() == PacketType.REGISTRATION) {
					String trimmedUsername = ((PacketRegistration) packet).getUsername().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
					Utils.checkRegistration(((PacketRegistration) packet).getVersion(), trimmedUsername, ((PacketRegistration) packet).getPassword());
					this.connectionSocket.setUsername(trimmedUsername);
					Utils.displayToLog("Socket Player " + this.connectionSocket.getSocket().getInetAddress().getHostAddress() + " : " + this.connectionSocket.getId() + " registerd as: " + trimmedUsername);
				}
			} catch (AuthenticationFailedException exception) {
				this.connectionSocket.sendAuthenticationFailure(exception.getLocalizedMessage());
			}
		}
		return true;
	}

	synchronized void end()
	{
		this.keepGoing = false;
	}
}
