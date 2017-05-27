package it.polimi.ingsw.lim.client.game;

import it.polimi.ingsw.lim.common.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardCharacter;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardVenture;
import it.polimi.ingsw.lim.common.game.CouncilPalaceReward;

import java.util.ArrayList;
import java.util.List;

public class GameUtils
{
	private GameUtils()
	{
	}

	private static final List<DevelopmentCardBuilding> DEVELOPMENT_CARDS_BUILDING = new ArrayList<>();
	private static final List<DevelopmentCardCharacter> DEVELOPMENT_CARDS_CHARACTER = new ArrayList<>();
	private static final List<DevelopmentCardTerritory> DEVELOPMENT_CARDS_TERRITORY = new ArrayList<>();
	private static final List<DevelopmentCardVenture> DEVELOPMENT_CARDS_VENTURE = new ArrayList<>();
	private static final List<CouncilPalaceReward> COUNCIL_PALACE_REWARDS = new ArrayList<>();

	public static List<DevelopmentCardBuilding> getDevelopmentCardsBuilding()
	{
		return GameUtils.DEVELOPMENT_CARDS_BUILDING;
	}

	public static List<DevelopmentCardCharacter> getDevelopmentCardsCharacter()
	{
		return GameUtils.DEVELOPMENT_CARDS_CHARACTER;
	}

	public static List<DevelopmentCardTerritory> getDevelopmentCardsTerritory()
	{
		return GameUtils.DEVELOPMENT_CARDS_TERRITORY;
	}

	public static List<DevelopmentCardVenture> getDevelopmentCardsVenture()
	{
		return GameUtils.DEVELOPMENT_CARDS_VENTURE;
	}

	public static List<CouncilPalaceReward> getCouncilPalaceRewards()
	{
		return GameUtils.COUNCIL_PALACE_REWARDS;
	}
}
