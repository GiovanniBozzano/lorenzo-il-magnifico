package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.cards.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformations;
import it.polimi.ingsw.lim.common.game.player.PlayerData;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.*;

public class PacketGameStarted extends Packet
{
	private final List<DevelopmentCardInformations> developmentCardsInformations;
	private final List<LeaderCardInformations> leaderCardsInformations;
	private final List<ExcommunicationTileInformations> excommunicationTilesInformations;
	private final List<CouncilPalaceRewardInformations> councilPalaceRewardInformations;
	private final Map<Period, Integer> excommunicationTiles;
	private final Map<Integer, PlayerData> playersData;

	public PacketGameStarted(List<DevelopmentCardInformations> developmentCardsInformations, List<LeaderCardInformations> leaderCardsInformations, List<ExcommunicationTileInformations> excommunicationTilesInformations, List<CouncilPalaceRewardInformations> councilPalaceRewardInformations, Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerData> playersData)
	{
		super(PacketType.GAME_STARTED);
		this.developmentCardsInformations = new ArrayList<>(developmentCardsInformations);
		this.leaderCardsInformations = new ArrayList<>(leaderCardsInformations);
		this.excommunicationTilesInformations = new ArrayList<>(excommunicationTilesInformations);
		this.councilPalaceRewardInformations = new ArrayList<>(councilPalaceRewardInformations);
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

	public List<CouncilPalaceRewardInformations> getCouncilPalaceRewardInformations()
	{
		return this.councilPalaceRewardInformations;
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
