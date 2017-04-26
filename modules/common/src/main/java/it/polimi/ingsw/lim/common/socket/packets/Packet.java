package it.polimi.ingsw.lim.common.socket.packets;

import it.polimi.ingsw.lim.common.enums.PacketType;

import java.io.Serializable;

public class Packet implements Serializable
{
	private final PacketType packetType;

	protected Packet(PacketType packetType)
	{
		this.packetType = packetType;
	}

	public PacketType getPacketType()
	{
		return this.packetType;
	}
}
