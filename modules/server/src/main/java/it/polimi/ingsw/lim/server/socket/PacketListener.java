package it.polimi.ingsw.lim.server.socket;

import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.socket.packets.client.PacketRoomEntry;
import it.polimi.ingsw.lim.common.socket.packets.client.PacketRoomExit;
import it.polimi.ingsw.lim.server.Server;

import java.io.IOException;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private boolean keepGoing = true;
	private final ConnectionSocket connectionSocket;

	PacketListener(ConnectionSocket connectionSocket)
	{
		this.connectionSocket = connectionSocket;
	}

	@Override
	public void run()
	{
		if (!this.keepGoing || !this.connectionSocket.handleHandshake()) {
			this.connectionSocket.disconnect();
			return;
		}
		this.connectionSocket.sendHandshakeCorrect();
		while (this.keepGoing) {
			try {
				Packet packet = (Packet) this.connectionSocket.getIn().readObject();
				if (packet == null) {
					this.connectionSocket.disconnect();
					return;
				}
				switch (packet.getPacketType()) {
					case REQUEST_ROOM_LIST:
						this.connectionSocket.handleRequestRoomList();
						break;
					case ROOM_ENTRY:
						this.connectionSocket.handleRoomEntry(((PacketRoomEntry) packet).getId());
						break;
					case ROOM_EXIT:
						this.connectionSocket.handleRoomExit(((PacketRoomExit) packet).getId());
						break;
					case CHAT_MESSAGE:
						this.connectionSocket.handleChatMessage(((PacketChatMessage) packet).getText());
						break;
					default:
				}
			} catch (ClassNotFoundException | IOException exception) {
				Server.getLogger().log(Level.INFO, "Socket Client " + this.connectionSocket.getId() + ":" + this.connectionSocket.getName() + " disconnected.", exception);
				Server.getInstance().displayToLog("Socket Client " + this.connectionSocket.getId() + ":" + this.connectionSocket.getName() + " disconnected.");
				this.close();
				this.connectionSocket.disconnect();
			}
		}
	}

	private synchronized void close()
	{
		this.keepGoing = false;
	}
}
