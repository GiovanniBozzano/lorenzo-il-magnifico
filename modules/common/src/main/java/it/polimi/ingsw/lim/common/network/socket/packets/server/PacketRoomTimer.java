package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketRoomTimer extends Packet
{
	private final int timer;

	public PacketRoomTimer(int timer)
	{
		super(PacketType.ROOM_TIMER);
		this.timer = timer;
	}

	public int getTimer()
	{
		return this.timer;
	}
}
