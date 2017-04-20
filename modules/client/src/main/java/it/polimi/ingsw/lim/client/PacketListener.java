package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.common.packets.Packet;
import it.polimi.ingsw.lim.common.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.io.IOException;
import java.util.logging.Level;

public class PacketListener extends Thread
{
	private boolean keepGoing = true;

	@Override
	public void run()
	{
		while (keepGoing) {
			try {
				Packet packet = (Packet) Client.getInstance().getIn().readObject();
				if (packet == null) {
					Client.getInstance().disconnect();
					return;
				}
				switch (packet.getPacketType()) {
					case CHAT_MESSAGE:
						PacketListener.handleChatMessage((PacketChatMessage) packet);
						break;
				}
			} catch (ClassNotFoundException | IOException exception) {
				if (!keepGoing) {
					return;
				}
				Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				Client.getInstance().disconnect();
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
}
