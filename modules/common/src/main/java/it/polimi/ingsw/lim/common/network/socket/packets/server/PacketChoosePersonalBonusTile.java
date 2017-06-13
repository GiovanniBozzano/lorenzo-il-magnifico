package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketChoosePersonalBonusTile extends Packet
{
	private final int index;

	public PacketChoosePersonalBonusTile(int index)
	{
		super(PacketType.CHOOSE_PERSONAL_BONUS_TILE);
		this.index = index;
	}

	public int getIndex()
	{
		return this.index;
	}
}
