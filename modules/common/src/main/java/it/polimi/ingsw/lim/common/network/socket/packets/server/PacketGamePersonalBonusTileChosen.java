package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGamePersonalBonusTileChosen extends Packet
{
	private int choicePlayerIndex;

	public PacketGamePersonalBonusTileChosen(int choicePlayerIndex)
	{
		super(PacketType.GAME_PERSONAL_BONUS_TILE_CHOSEN);
		this.choicePlayerIndex = choicePlayerIndex;
	}

	public int getChoicePlayerIndex()
	{
		return this.choicePlayerIndex;
	}
}
