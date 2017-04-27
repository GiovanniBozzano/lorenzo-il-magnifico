package it.polimi.ingsw.lim.common.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;

public class PacketRoomExit extends Packet
{
	private int id;

	public PacketRoomExit()
	{
		super(PacketType.ROOM_ENTRY);
	}

	public int getId()
	{
		return this.id;
	}
}
