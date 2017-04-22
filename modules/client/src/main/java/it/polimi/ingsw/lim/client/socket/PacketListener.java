package it.polimi.ingsw.lim.client.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.packets.Packet;
import it.polimi.ingsw.lim.common.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.packets.server.PacketLogMessage;

import java.io.IOException;
import java.util.logging.Level;

public class PacketListener extends Thread
{
	private boolean keepGoing = true;

	@Override
	public void run()
	{
		while (this.keepGoing) {
			try {
				Packet packet = (Packet) Client.getInstance().getIn().readObject();
				if (packet == null) {
					Client.getInstance().disconnect();
					return;
				}
				switch (packet.getPacketType()) {
					case LOG_MESSAGE:
						PacketListener.handleLogMessage((PacketLogMessage) packet);
						break;
					case CHAT_MESSAGE:
						PacketListener.handleChatMessage((PacketChatMessage) packet);
						break;
					default:
				}
			} catch (ClassNotFoundException | IOException exception) {
				Client.getLogger().log(Level.INFO, "The connection has been closed.", exception);
				if (Client.getInstance() != null) {
					Client.getInstance().disconnect();
				}
			}
		}
	}

	public synchronized void close()
	{
		this.keepGoing = false;
	}

	private static void handleChatMessage(PacketChatMessage packet)
	{
		Client.getLogger().log(Level.INFO, packet.getText());
	}

	private static void handleLogMessage(PacketLogMessage packet)
	{
		Client.getLogger().log(Level.INFO, packet.getText());
	}
}
