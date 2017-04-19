package it.polimi.ingsw.lim.common.packets;

import it.polimi.ingsw.lim.common.enums.PacketType;

public class Packet
{
	private PacketType packetType;

	Packet(PacketType packetType)
	{
		this.packetType = packetType;
	}

	public PacketType getPacketType()
	{
		return this.packetType;
	}
}
