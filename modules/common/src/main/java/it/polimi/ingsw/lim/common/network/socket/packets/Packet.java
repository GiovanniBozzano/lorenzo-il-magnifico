package it.polimi.ingsw.lim.common.network.socket.packets;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;

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
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public PacketType getPacketType()
	{
		return this.packetType;
	}
}
