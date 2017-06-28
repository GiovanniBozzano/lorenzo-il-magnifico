package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.LinkedHashMap;
import java.util.Map;

public class PacketGameEnded extends Packet
{
	private final Map<Integer, Integer> playersScores;
	private final Map<Integer, Integer> playerIndexesVictoryPointsRecord;

	public PacketGameEnded(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord)
	{
		super(PacketType.GAME_ENDED);
		this.playersScores = new LinkedHashMap<>(playersScores);
		this.playerIndexesVictoryPointsRecord = new LinkedHashMap<>(playerIndexesVictoryPointsRecord);
	}

	public Map<Integer, Integer> getPlayersScores()
	{
		return this.playersScores;
	}

	public Map<Integer, Integer> getPlayerIndexesVictoryPointsRecord()
	{
		return this.playerIndexesVictoryPointsRecord;
	}
}
