package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameDisconnectionOther extends Packet
{
	private final int playerIndex;

	public PacketGameDisconnectionOther(int playerIndex)
	{
		super(PacketType.GAME_DISCONNECTION_OTHER);
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex()
	{
		return this.playerIndex;
	}
}
