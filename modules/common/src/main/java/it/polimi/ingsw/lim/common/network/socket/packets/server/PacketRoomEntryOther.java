package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketRoomEntryOther extends Packet
{
	private final String name;

	public PacketRoomEntryOther(String name)
	{
		super(PacketType.ROOM_ENTRY_OTHER);
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}
}
