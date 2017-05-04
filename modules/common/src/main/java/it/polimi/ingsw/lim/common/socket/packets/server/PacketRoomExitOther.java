package it.polimi.ingsw.lim.common.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;

public class PacketRoomExitOther extends Packet
{
	private final String name;

	public PacketRoomExitOther(String name)
	{
		super(PacketType.ROOM_EXIT_OTHER);
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}
}
