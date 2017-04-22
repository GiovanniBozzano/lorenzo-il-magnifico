package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.packets.Packet;
import it.polimi.ingsw.lim.common.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.packets.client.PacketHandshake;
import it.polimi.ingsw.lim.common.utils.Constants;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.io.IOException;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private boolean keepGoing = true;
	private final ClientConnection clientConnection;

	PacketListener(ClientConnection clientConnection)
	{
		this.clientConnection = clientConnection;
	}

	@Override
	public void run()
	{
		if (!this.keepGoing || !this.handleHandshake()) {
			this.clientConnection.disconnect();
			return;
		}
		this.clientConnection.setIsConnected(true);
		while (this.keepGoing && this.clientConnection.getIsConnected()) {
			try {
				Packet packet = (Packet) this.clientConnection.getIn().readObject();
				if (packet == null) {
					this.clientConnection.disconnect();
					return;
				}
				switch (packet.getPacketType()) {
					case CHAT_MESSAGE:
						this.handleChatMessage((PacketChatMessage) packet);
						break;
					default:
				}
			} catch (ClassNotFoundException | IOException exception) {
				Server.getLogger().log(Level.INFO, "Client " + this.clientConnection.getId() + ":" + this.clientConnection.getName() + " disconnected.", exception);
				this.close();
				this.clientConnection.disconnect();
			}
		}
	}

	public synchronized void close()
	{
		this.keepGoing = false;
	}

	private boolean handleHandshake()
	{
		try {
			Packet packet = (Packet) this.clientConnection.getIn().readObject();
			if (packet.getPacketType() == PacketType.HANDSHAKE && ((PacketHandshake) packet).getVersion().equals(Constants.VERSION)) {
				this.clientConnection.sendLogMessage("Connected to server.");
				return true;
			} else {
				this.clientConnection.sendLogMessage("Client version not compatible with the Server.");
				return false;
			}
		} catch (ClassNotFoundException | IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			this.clientConnection.sendLogMessage("Client version not compatible with the Server.");
			return false;
		}
	}

	private void handleChatMessage(PacketChatMessage packet)
	{
		for (ClientConnection clientConnection : Server.getInstance().getClientConnections()) {
			if (clientConnection != this.clientConnection) {
				clientConnection.sendChatMessage(this.clientConnection.getName() + ": " + packet.getText());
			}
		}
	}

	public ClientConnection getClientConnection()
	{
		return this.clientConnection;
	}
}
