package it.polimi.ingsw.lim.common.packets;

import it.polimi.ingsw.lim.common.enums.PacketType;

import java.io.Serializable;

public class Packet implements Serializable
{
	private final PacketType packetType;

	public Packet(PacketType packetType)
	{
		this.packetType = packetType;
	}

	public PacketType getPacketType()
	{
		return this.packetType;
	}
}
