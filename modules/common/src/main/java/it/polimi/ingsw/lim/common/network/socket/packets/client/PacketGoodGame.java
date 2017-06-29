package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGoodGame extends Packet
{
	private final int playerIndex;

	public PacketGoodGame(int playerIndex)
	{
		super(PacketType.GOOD_GAME);
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex()
	{
		return this.playerIndex;
	}
}
