package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketGameLeaderCardChoiceRequest extends Packet
{
	private final List<Integer> availableLeaderCardsIndexes;

	public PacketGameLeaderCardChoiceRequest(List<Integer> availableLeaderCardsIndexes)
	{
		super(PacketType.GAME_LEADER_CARD_CHOICE_REQUEST);
		this.availableLeaderCardsIndexes = new ArrayList<>(availableLeaderCardsIndexes);
	}

	public List<Integer> getAvailableLeaderCardsIndexes()
	{
		return this.availableLeaderCardsIndexes;
	}
}
