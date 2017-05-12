package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketRoomEntry extends Packet
{
	private final int id;

	public PacketRoomEntry(int id)
	{
		super(PacketType.ROOM_ENTRY);
		this.id = id;
	}

	public int getId()
	{
		return this.id;
	}
}
