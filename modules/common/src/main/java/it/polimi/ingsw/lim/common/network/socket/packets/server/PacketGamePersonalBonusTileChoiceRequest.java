package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketGamePersonalBonusTileChoiceRequest extends Packet
{
	private List<PersonalBonusTileInformations> personalBonusTilesInformations;

	public PacketGamePersonalBonusTileChoiceRequest(List<PersonalBonusTileInformations> personalBonusTilesInformations)
	{
		super(PacketType.GAME_PERSONAL_BONUS_TILE_CHOICE_REQUEST);
		this.personalBonusTilesInformations = new ArrayList<>(personalBonusTilesInformations);
	}

	public List<PersonalBonusTileInformations> getPersonalBonusTilesInformations()
	{
		return this.personalBonusTilesInformations;
	}
}
