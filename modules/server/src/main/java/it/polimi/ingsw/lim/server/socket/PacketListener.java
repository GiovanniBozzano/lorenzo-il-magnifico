package it.polimi.ingsw.lim.server.socket;

import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.packets.Packet;
import it.polimi.ingsw.lim.common.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.packets.client.PacketHandshake;
import it.polimi.ingsw.lim.common.utils.Constants;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.IConnection;
import it.polimi.ingsw.lim.server.Server;

import java.io.IOException;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private boolean keepGoing = true;
	private final SocketConnection socketConnection;

	PacketListener(SocketConnection socketConnection)
	{
		this.socketConnection = socketConnection;
	}

	@Override
	public void run()
	{
		if (!this.keepGoing || !this.handleHandshake()) {
			this.socketConnection.disconnect();
			return;
		}
		while (this.keepGoing) {
			try {
				Packet packet = (Packet) this.socketConnection.getIn().readObject();
				if (packet == null) {
					this.socketConnection.disconnect();
					return;
				}
				switch (packet.getPacketType()) {
					case CHAT_MESSAGE:
						this.socketConnection.handleChatMessage(((PacketChatMessage) packet).getText());
						break;
					default:
				}
			} catch (ClassNotFoundException | IOException exception) {
				Server.getLogger().log(Level.INFO, "Socket Client " + this.socketConnection.getId() + ":" + this.socketConnection.getName() + " disconnected.", exception);
				Server.getInstance().displayToLog("Socket Client " + this.socketConnection.getId() + ":" + this.socketConnection.getName() + " disconnected.", FontType.NORMAL);
				this.close();
				this.socketConnection.disconnect();
			}
		}
	}

	private boolean handleHandshake()
	{
		try {
			Packet packet = (Packet) this.socketConnection.getIn().readObject();
			if (packet.getPacketType() == PacketType.HANDSHAKE && ((PacketHandshake) packet).getVersion().equals(Constants.VERSION)) {
				this.socketConnection.sendLogMessage("Connected to server.");
				return true;
			} else {
				this.socketConnection.sendLogMessage("Client version not compatible with the Server.");
				return false;
			}
		} catch (ClassNotFoundException | IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			this.socketConnection.sendLogMessage("Client version not compatible with the Server.");
			return false;
		}
	}

	private synchronized void close()
	{
		this.keepGoing = false;
	}

	public IConnection getSocketConnection()
	{
		return this.socketConnection;
	}
}
