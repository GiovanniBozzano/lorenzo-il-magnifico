package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameTimer extends Packet
{
	private final int timer;

	public PacketGameTimer(int timer)
	{
		super(PacketType.GAME_TIMER);
		this.timer = timer;
	}

	public int getTimer()
	{
		return this.timer;
	}
}
