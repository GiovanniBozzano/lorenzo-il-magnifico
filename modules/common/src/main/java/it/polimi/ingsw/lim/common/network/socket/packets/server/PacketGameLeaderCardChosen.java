package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameLeaderCardChosen extends Packet
{
	private final int choicePlayerIndex;

	public PacketGameLeaderCardChosen(int choicePlayerIndex)
	{
		super(PacketType.GAME_LEADER_CARD_CHOSEN);
		this.choicePlayerIndex = choicePlayerIndex;
	}

	public int getChoicePlayerIndex()
	{
		return this.choicePlayerIndex;
	}
}
