package it.polimi.ingsw.lim.common.packets;

import it.polimi.ingsw.lim.common.enums.PacketType;

import java.io.Serializable;

public class PacketHandshake extends Packet implements Serializable
{
	private static final long serialVersionUID = 1L;

	public PacketHandshake()
	{
		super(PacketType.HANDSHAKE);
	}
}
