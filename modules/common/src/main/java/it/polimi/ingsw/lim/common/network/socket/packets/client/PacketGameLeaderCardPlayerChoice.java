package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameLeaderCardPlayerChoice extends Packet
{
	private final int leaderCardIndex;

	public PacketGameLeaderCardPlayerChoice(int leaderCardIndex)
	{
		super(PacketType.GAME_LEADER_CARD_PLAYER_CHOICE);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
