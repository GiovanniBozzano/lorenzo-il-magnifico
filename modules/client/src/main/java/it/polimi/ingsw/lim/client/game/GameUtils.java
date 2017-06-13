package it.polimi.ingsw.lim.client.game;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.*;

import java.util.*;

public class GameUtils
{
	private static final List<DevelopmentCardInformations> DEVELOPMENT_CARDS_BUILDING = new ArrayList<>();
	private static final List<DevelopmentCardInformations> DEVELOPMENT_CARDS_CHARACTER = new ArrayList<>();
	private static final List<DevelopmentCardInformations> DEVELOPMENT_CARDS_TERRITORY = new ArrayList<>();
	private static final List<DevelopmentCardInformations> DEVELOPMENT_CARDS_VENTURE = new ArrayList<>();
	private static final List<LeaderCardInformations> LEADER_CARDS_INFORMATIONS = new ArrayList<>();
	private static final List<ExcommunicationTileInformations> EXCOMMUNICATION_TILES_INFORMATIONS = new ArrayList<>();
	private static final Map<Period, Integer> EXCOMMUNICATION_TILES = new EnumMap<>(Period.class);
	private static final Map<Integer, PlayerData> PLAYERS_DATA = new HashMap<>();
	private static final List<CouncilPalaceRewardInformations> COUNCIL_PALACE_REWARDS = new ArrayList<>();

	private GameUtils()
	{
	}

	public static List<DevelopmentCardInformations> getDevelopmentCardsBuilding()
	{
		return GameUtils.DEVELOPMENT_CARDS_BUILDING;
	}

	public static List<DevelopmentCardInformations> getDevelopmentCardsCharacter()
	{
		return GameUtils.DEVELOPMENT_CARDS_CHARACTER;
	}

	public static List<DevelopmentCardInformations> getDevelopmentCardsTerritory()
	{
		return GameUtils.DEVELOPMENT_CARDS_TERRITORY;
	}

	public static List<DevelopmentCardInformations> getDevelopmentCardsVenture()
	{
		return GameUtils.DEVELOPMENT_CARDS_VENTURE;
	}

	public static List<CouncilPalaceRewardInformations> getCouncilPalaceRewards()
	{
		return GameUtils.COUNCIL_PALACE_REWARDS;
	}

	public static List<LeaderCardInformations> getLeaderCardsInformations()
	{
		return GameUtils.LEADER_CARDS_INFORMATIONS;
	}

	public static List<ExcommunicationTileInformations> getExcommunicationTilesInformations()
	{
		return GameUtils.EXCOMMUNICATION_TILES_INFORMATIONS;
	}

	public static Map<Period, Integer> getExcommunicationTiles()
	{
		return GameUtils.EXCOMMUNICATION_TILES;
	}

	public static Map<Integer, PlayerData> getPlayersData()
	{
		return GameUtils.PLAYERS_DATA;
	}
}
