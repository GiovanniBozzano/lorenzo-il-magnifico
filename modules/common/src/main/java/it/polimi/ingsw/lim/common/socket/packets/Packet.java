package it.polimi.ingsw.lim.common.socket.packets;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;

public class Packet implements Serializable
{
	private final PacketType packetType;

	public Packet(PacketType packetType)
	{
		this.packetType = packetType;
	}

	public void send(ObjectOutputStream out)
	{
		try {
			out.writeObject(this);
		} catch (IOException exception) {
			Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public PacketType getPacketType()
	{
		return this.packetType;
	}
}
