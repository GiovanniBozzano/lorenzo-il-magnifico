package it.polimi.ingsw.lim.client.game;

import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.DevelopmentCardInformations;

import java.util.ArrayList;
import java.util.List;

public class GameUtils
{
	private GameUtils()
	{
	}

	private static final List<DevelopmentCardInformations> DEVELOPMENT_CARDS_BUILDING = new ArrayList<>();
	private static final List<DevelopmentCardInformations> DEVELOPMENT_CARDS_CHARACTER = new ArrayList<>();
	private static final List<DevelopmentCardInformations> DEVELOPMENT_CARDS_TERRITORY = new ArrayList<>();
	private static final List<DevelopmentCardInformations> DEVELOPMENT_CARDS_VENTURE = new ArrayList<>();
	private static final List<CouncilPalaceRewardInformations> COUNCIL_PALACE_REWARDS = new ArrayList<>();

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
}
