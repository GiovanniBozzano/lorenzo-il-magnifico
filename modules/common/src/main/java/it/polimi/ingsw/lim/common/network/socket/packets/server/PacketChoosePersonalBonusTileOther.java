package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketChoosePersonalBonusTileOther extends Packet
{
	private final int index;

	public PacketChoosePersonalBonusTileOther(int index)
	{
		super(PacketType.CHOOSE_PERSONAL_BONUS_TILE_OTHER);
		this.index = index;
	}

	public int getIndex()
	{
		return this.index;
	}
}
