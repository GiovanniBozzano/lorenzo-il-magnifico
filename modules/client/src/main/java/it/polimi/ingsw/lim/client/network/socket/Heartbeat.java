package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.io.ObjectOutputStream;
import java.util.logging.Level;

public class Heartbeat extends Thread
{
	private boolean keepGoing = true;
	private ObjectOutputStream out;

	Heartbeat(ObjectOutputStream out)
	{
		this.out = out;
	}

	@Override
	public void run()
	{
		while (this.keepGoing) {
			new Packet(PacketType.HEARTBEAT).send(this.out);
			try {
				this.wait(1000L);
			} catch (InterruptedException exception) {
				Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				Thread.currentThread().interrupt();
			}
		}
	}

	public synchronized void close()
	{
		this.keepGoing = false;
	}
}
