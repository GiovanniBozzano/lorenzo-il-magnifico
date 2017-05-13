package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketHandshake;
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
		if (!this.waitHandshake()) {
			return;
		}
		this.connectionSocket.sendHandshakeConfirmation();
		while (this.keepGoing) {
			Packet packet;
			try {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				Server.getLogger().log(Level.INFO, "Socket Client " + this.connectionSocket.getId() + (this.connectionSocket.getName() != null ? " : " + this.connectionSocket.getName() : "") + " disconnected.", exception);
				if (!this.keepGoing) {
					return;
				}
				this.connectionSocket.disconnect(false, null);
				return;
			}
			if (packet == null) {
				this.connectionSocket.disconnect(true, null);
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

	private boolean waitHandshake()
	{
		Packet packet;
		try {
			do {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} while (packet.getPacketType() != PacketType.HANDSHAKE);
		} catch (ClassNotFoundException | IOException exception) {
			Server.getLogger().log(Level.INFO, "Handshake failed.", exception);
			if (!this.keepGoing) {
				return false;
			}
			this.connectionSocket.disconnect(false, null);
			return false;
		}
		if (!((PacketHandshake) packet).getVersion().equals(CommonUtils.VERSION)) {
			if (!this.keepGoing) {
				return false;
			}
			this.connectionSocket.disconnect(false, "Client version not compatible with the Server.");
			return false;
		}
		String name = ((PacketHandshake) packet).getName().replaceAll("^\\s+|\\s+$", "");
		if (!name.matches("^[\\w\\-]{4,16}$")) {
			if (!this.keepGoing) {
				return false;
			}
			this.connectionSocket.disconnect(false, null);
			return false;
		}
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getName() != null && connection.getName().equals(name)) {
				if (!this.keepGoing) {
					return false;
				}
				this.connectionSocket.disconnect(false, "Client name is already taken.");
				return false;
			}
		}
		if (!this.keepGoing) {
			return false;
		}
		this.setName(name);
		return true;
	}

	synchronized void close()
	{
		this.keepGoing = false;
	}
}
