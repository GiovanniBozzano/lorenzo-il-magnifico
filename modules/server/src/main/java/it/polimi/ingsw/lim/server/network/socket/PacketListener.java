package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomCreation;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomEntry;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.io.IOException;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private final ConnectionSocket connectionSocket;
	private boolean keepGoing = true;

	PacketListener(ConnectionSocket connectionSocket)
	{
		this.connectionSocket = connectionSocket;
	}

	@Override
	public void run()
	{
		if (!this.keepGoing || !this.connectionSocket.handleHandshake()) {
			this.connectionSocket.disconnect(true);
			return;
		}
		this.connectionSocket.sendHandshakeConfirmation();
		while (this.keepGoing) {
			Packet packet;
			try {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				if (!this.keepGoing) {
					return;
				}
				Server.getLogger().log(Level.INFO, "Socket Client " + this.connectionSocket.getId() + ":" + this.connectionSocket.getName() + " disconnected.", exception);
				Utils.displayToLog("Socket Client " + this.connectionSocket.getId() + ":" + this.connectionSocket.getName() + " disconnected.");
				this.connectionSocket.disconnect(false);
				return;
			}
			if (packet == null) {
				this.connectionSocket.disconnect(true);
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

	public synchronized void close()
	{
		this.keepGoing = false;
	}
}
