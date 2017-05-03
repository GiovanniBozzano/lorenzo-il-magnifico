package it.polimi.ingsw.lim.common.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;

public class PacketRoomCreation extends Packet
{
	private final String name;

	public PacketRoomCreation(String name)
	{
		super(PacketType.ROOM_CREATION);
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}
}
