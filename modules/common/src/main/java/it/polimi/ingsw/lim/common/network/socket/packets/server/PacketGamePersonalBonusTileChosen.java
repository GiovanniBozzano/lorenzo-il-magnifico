package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGamePersonalBonusTileChosen extends Packet
{
	private int choicePlayerIndex;
	private int choicePersonalBonusTileIndex;

	public PacketGamePersonalBonusTileChosen(int choicePlayerIndex, int choicePersonalBonusTileIndex)
	{
		super(PacketType.GAME_PERSONAL_BONUS_TILE_CHOSEN);
		this.choicePlayerIndex = choicePlayerIndex;
		this.choicePersonalBonusTileIndex = choicePersonalBonusTileIndex;
	}

	public int getChoicePlayerIndex()
	{
		return this.choicePlayerIndex;
	}

	public int getChoicePersonalBonusTileIndex()
	{
		return this.choicePersonalBonusTileIndex;
	}
}
