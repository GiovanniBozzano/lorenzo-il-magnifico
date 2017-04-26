package it.polimi.ingsw.lim.common.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;

public class PacketLogMessage extends Packet
{
	private final String text;

	public PacketLogMessage(String text)
	{
		super(PacketType.LOG_MESSAGE);
		this.text = text;
	}

	public String getText()
	{
		return this.text;
	}
}
