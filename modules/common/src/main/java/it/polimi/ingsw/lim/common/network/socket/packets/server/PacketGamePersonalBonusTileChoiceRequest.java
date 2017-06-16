package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketGamePersonalBonusTileChoiceRequest extends Packet
{
	private List<Integer> personalBonusTilesInformations;

	public PacketGamePersonalBonusTileChoiceRequest(List<Integer> personalBonusTilesInformations)
	{
		super(PacketType.GAME_PERSONAL_BONUS_TILE_CHOICE_REQUEST);
		this.personalBonusTilesInformations = new ArrayList<>(personalBonusTilesInformations);
	}

	public List<Integer> getPersonalBonusTilesInformations()
	{
		return this.personalBonusTilesInformations;
	}
}
