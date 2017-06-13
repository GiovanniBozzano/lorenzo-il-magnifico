package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.LeaderCardInformations;
import it.polimi.ingsw.lim.common.game.PlayerData;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.*;

public class PacketGameStarted extends Packet
{
	private final List<DevelopmentCardInformations> developmentCardsInformations;
	private final List<LeaderCardInformations> leaderCardsInformations;
	private final List<ExcommunicationTileInformations> excommunicationTilesInformations;
	private final Map<Period, Integer> excommunicationTiles;
	private final Map<Integer, PlayerData> playersData;

	public PacketGameStarted(List<DevelopmentCardInformations> developmentCardsInformations, List<LeaderCardInformations> leaderCardsInformations, List<ExcommunicationTileInformations> excommunicationTilesInformations, Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerData> playersData)
	{
		super(PacketType.GAME_STARTED);
		this.developmentCardsInformations = new ArrayList<>(developmentCardsInformations);
		this.leaderCardsInformations = new ArrayList<>(leaderCardsInformations);
		this.excommunicationTilesInformations = new ArrayList<>(excommunicationTilesInformations);
		this.excommunicationTiles = new EnumMap<>(excommunicationTiles);
		this.playersData = new HashMap<>(playersData);
	}

	public List<DevelopmentCardInformations> getDevelopmentCardsInformations()
	{
		return this.developmentCardsInformations;
	}

	public List<LeaderCardInformations> getLeaderCardsInformations()
	{
		return this.leaderCardsInformations;
	}

	public List<ExcommunicationTileInformations> getExcommunicationTilesInformations()
	{
		return this.excommunicationTilesInformations;
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
