package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketLogin;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomCreation;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomEntry;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;

import java.io.IOException;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private final ConnectionSocket connectionSocket;
	private volatile boolean keepGoing = true;

	PacketListener(ConnectionSocket connectionSocket)
	{
		this.connectionSocket = connectionSocket;
	}

	@Override
	public void run()
	{
		if (!this.waitLogin()) {
			return;
		}
		this.connectionSocket.sendLoginConfirmation();
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
			switch (packet.getPacketType()) {
				case ROOM_LIST_REQUEST:
					this.connectionSocket.handleRequestRoomList();
					break;
				case ROOM_CREATION:
					this.connectionSocket.handleRoomCreation(((PacketRoomCreation) packet).getName());
					break;
				case ROOM_ENTRY:
					this.connectionSocket.handleRoomEntry(((PacketRoomEntry) packet).getId());
					break;
				case ROOM_EXIT:
					this.connectionSocket.handleRoomExit();
					break;
				case CHAT_MESSAGE:
					this.connectionSocket.handleChatMessage(((PacketChatMessage) packet).getText());
					break;
				default:
			}
		}
	}

	private boolean waitLogin()
	{
		while (this.connectionSocket.getUsername() == null) {
			Packet packet;
			try {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				Server.getLogger().log(Level.INFO, "Socket Client " + this.connectionSocket.getId() + " disconnected.", exception);
				if (!this.keepGoing) {
					return false;
				}
				this.connectionSocket.disconnect(false, null);
				return false;
			}
			if (packet.getPacketType() != PacketType.LOGIN) {
				continue;
			}
			if (!((PacketLogin) packet).getVersion().equals(CommonUtils.VERSION)) {
				this.connectionSocket.sendLoginFailure("Client version not compatible with the Server.");
				continue;
			}
			String username = ((PacketLogin) packet).getUsername().replaceAll("^\\s+|\\s+$", "");
			if (!username.matches("^[\\w\\-]{4,16}$")) {
				this.connectionSocket.sendLoginFailure("Incorrect username.");
				continue;
			}
			boolean alreadyLoggedIn = false;
			for (Connection connection : Server.getInstance().getConnections()) {
				if (connection.getUsername() != null && connection.getUsername().equals(username)) {
					this.connectionSocket.sendLoginFailure("Already logged in.");
					alreadyLoggedIn = true;
					break;
				}
			}
			if (alreadyLoggedIn) {
				continue;
			}
			this.connectionSocket.setUsername(username);
		}
		return true;
	}

	synchronized void end()
	{
		this.keepGoing = false;
	}
}
