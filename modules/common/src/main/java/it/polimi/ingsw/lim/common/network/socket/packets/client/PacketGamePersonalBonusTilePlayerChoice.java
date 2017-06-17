package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGamePersonalBonusTilePlayerChoice extends Packet
{
	private final int personalBonusTileIndex;

	public PacketGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex)
	{
		super(PacketType.GAME_PERSONAL_BONUS_TILE_PLAYER_CHOICE);
		this.personalBonusTileIndex = personalBonusTileIndex;
	}

	public int getPersonalBonusTileIndex()
	{
		return this.personalBonusTileIndex;
	}
}
