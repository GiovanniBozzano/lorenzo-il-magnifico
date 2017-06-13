package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketChosenPersonalBonusTile extends Packet
{
	private final int playerIndex;
	private final int personalBonusTileIndex;

	public PacketChosenPersonalBonusTile(int playerIndex, int personalBonusTileIndex)
	{
		super(PacketType.CHOSEN_PERSONAL_BONUS_TILE);
		this.playerIndex = playerIndex;
		this.personalBonusTileIndex = personalBonusTileIndex;
	}

	public int getPlayerIndex()
	{
		return this.playerIndex;
	}

	public int getPersonalBonusTileIndex()
	{
		return this.personalBonusTileIndex;
	}
}
