package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketGamePersonalBonusTileChoiceRequest extends Packet
{
	private final List<Integer> availablePersonalBonusTilesIndexes;

	public PacketGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTilesIndexes)
	{
		super(PacketType.GAME_PERSONAL_BONUS_TILE_CHOICE_REQUEST);
		this.availablePersonalBonusTilesIndexes = new ArrayList<>(availablePersonalBonusTilesIndexes);
	}

	public List<Integer> getAvailablePersonalBonusTilesIndexes()
	{
		return this.availablePersonalBonusTilesIndexes;
	}
}
