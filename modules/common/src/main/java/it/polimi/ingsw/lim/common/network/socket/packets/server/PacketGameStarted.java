package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.player.PlayerData;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class PacketGameStarted extends Packet
{
	private final Map<Period, Integer> excommunicationTiles;
	private final Map<Integer, PlayerData> playersData;

	public PacketGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerData> playersData)
	{
		super(PacketType.GAME_STARTED);
		this.excommunicationTiles = new EnumMap<>(excommunicationTiles);
		this.playersData = new HashMap<>(playersData);
	}

	public Map<Period, Integer> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public Map<Integer, PlayerData> getPlayersData()
	{
		return this.playersData;
	}
}
