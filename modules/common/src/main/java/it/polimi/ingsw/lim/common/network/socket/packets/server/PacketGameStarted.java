package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class PacketGameStarted extends Packet
{
	private final Map<Period, Integer> excommunicationTiles;
	private final Map<Integer, PlayerIdentification> playersData;
	private final int ownPlayerIndex;

	public PacketGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex)
	{
		super(PacketType.GAME_STARTED);
		this.excommunicationTiles = new EnumMap<>(excommunicationTiles);
		this.playersData = new HashMap<>(playersData);
		this.ownPlayerIndex = ownPlayerIndex;
	}

	public Map<Period, Integer> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public Map<Integer, PlayerIdentification> getPlayersData()
	{
		return this.playersData;
	}

	public int getOwnPlayerIndex()
	{
		return this.ownPlayerIndex;
	}
}
