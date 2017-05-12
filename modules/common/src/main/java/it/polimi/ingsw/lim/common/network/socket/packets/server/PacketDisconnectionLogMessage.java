package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketDisconnectionLogMessage extends Packet
{
	private final String text;

	public PacketDisconnectionLogMessage(String text)
	{
		super(PacketType.DISCONNECTION_LOG_MESSAGE);
		this.text = text;
	}

	public String getText()
	{
		return this.text;
	}
}
