package it.polimi.ingsw.lim.client.game;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformation;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformation;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class GameStatusTest
{
	private final Map<CardType, Map<Integer, DevelopmentCardInformation>> developmentCardsInformation = new EnumMap<>(CardType.class);
	private final Map<Integer, LeaderCardInformation> leaderCardsInformation = new HashMap<>();
	private final Map<Integer, ExcommunicationTileInformation> excommunicationTilesInformation = new HashMap<>();
	private final Map<Integer, PersonalBonusTileInformation> personalBonusTilesInformation = new HashMap<>();
	private final Map<CardType, Map<Row, Integer>> currentDevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<FamilyMemberType, Integer> currentDices = new EnumMap<>(FamilyMemberType.class);
	private final Map<Integer, Integer> currentTurnOrder = new HashMap<>();
	private final Map<Integer, Integer> currentCouncilPalaceOrder = new HashMap<>();
	private final Map<Period, List<Integer>> currentExcommunicatedPlayers = new EnumMap<>(Period.class);

	@Before
	public void setUp()
	{
		Map<Integer, DevelopmentCardInformation> developmentCardsBuildingInformation = new HashMap<>();
		developmentCardsBuildingInformation.put(0, new DevelopmentCardBuildingInformation(null, null, new ArrayList<>(), new RewardInformation(null, new ArrayList<>()), 0, new ArrayList<>()));
		Map<Integer, DevelopmentCardInformation> developmentCardsCharacterInformation = new HashMap<>();
		developmentCardsCharacterInformation.put(0, new DevelopmentCardCharacterInformation(null, null, new ArrayList<>(), new RewardInformation(null, new ArrayList<>()), null));
		Map<Integer, DevelopmentCardInformation> developmentCardsTerritoryInformation = new HashMap<>();
		developmentCardsTerritoryInformation.put(0, new DevelopmentCardTerritoryInformation(null, null, new ArrayList<>(), new RewardInformation(null, new ArrayList<>()), 0, new ArrayList<>()));
		Map<Integer, DevelopmentCardInformation> developmentCardsVentureInformation = new HashMap<>();
		developmentCardsVentureInformation.put(0, new DevelopmentCardVentureInformation(null, null, new ArrayList<>(), new RewardInformation(null, new ArrayList<>()), 0));
		this.developmentCardsInformation.put(CardType.BUILDING, developmentCardsBuildingInformation);
		this.developmentCardsInformation.put(CardType.CHARACTER, developmentCardsCharacterInformation);
		this.developmentCardsInformation.put(CardType.TERRITORY, developmentCardsTerritoryInformation);
		this.developmentCardsInformation.put(CardType.VENTURE, developmentCardsVentureInformation);
		this.leaderCardsInformation.put(0, new LeaderCardModifierInformation(null, null, null, new ArrayList<>(), null));
		this.excommunicationTilesInformation.put(0, new ExcommunicationTileInformation(null, null));
		this.personalBonusTilesInformation.put(0, new PersonalBonusTileInformation(null, null, 0, new ArrayList<>(), 0, new ArrayList<>()));
		for (CardType cardType : CardType.values()) {
			Map<Row, Integer> currentDevelopmentCardsType = new EnumMap<>(Row.class);
			currentDevelopmentCardsType.put(Row.FIRST, 0);
			this.currentDevelopmentCards.put(cardType, currentDevelopmentCardsType);
		}
		this.currentDices.put(FamilyMemberType.BLACK, 0);
		this.currentTurnOrder.put(0, 0);
		this.currentCouncilPalaceOrder.put(0, 0);
		this.currentExcommunicatedPlayers.put(Period.FIRST, Collections.singletonList(0));
	}

	@Test
	public void testSetup()
	{
		GameStatus.getInstance().setup(this.developmentCardsInformation, this.leaderCardsInformation, this.excommunicationTilesInformation, this.personalBonusTilesInformation);
		for (CardType cardType : CardType.values()) {
			Assert.assertFalse(GameStatus.getInstance().getDevelopmentCards().get(cardType).isEmpty());
		}
		Assert.assertEquals(4, GameStatus.getInstance().getDevelopmentCards().size());
		Assert.assertFalse(GameStatus.getInstance().getLeaderCards().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getExcommunicationTiles().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getPersonalBonusTiles().isEmpty());
	}

	@Test
	public void testUpdateGameStatus()
	{
		Map<ResourceType, Integer> resourceAmounts = new EnumMap<>(ResourceType.class);
		resourceAmounts.put(ResourceType.COIN, 10);
		Map<FamilyMemberType, BoardPosition> familyMemberTypesBoardPositions = new EnumMap<>(FamilyMemberType.class);
		familyMemberTypesBoardPositions.put(FamilyMemberType.BLACK, BoardPosition.NONE);
		Map<Integer, Boolean> ownLeaderCardsHand = new HashMap<>();
		ownLeaderCardsHand.put(0, false);
		Map<CardType, List<Integer>> developmentCardsInformation = new EnumMap<>(CardType.class);
		for (CardType cardType : CardType.values()) {
			developmentCardsInformation.put(cardType, new ArrayList<>());
		}
		GameStatus.getInstance().updateGameStatus(new GameInformation(this.currentDevelopmentCards, this.currentDices, this.currentTurnOrder, this.currentCouncilPalaceOrder, this.currentExcommunicatedPlayers), Collections.singletonList(new PlayerInformation(0, 0, developmentCardsInformation, new HashMap<>(), 0, resourceAmounts, familyMemberTypesBoardPositions)), ownLeaderCardsHand);
		Assert.assertEquals(Row.FIRST, GameStatus.getInstance().getDevelopmentCardRow(CardType.BUILDING, 0));
		for (CardType cardType : CardType.values()) {
			Assert.assertFalse(GameStatus.getInstance().getCurrentDevelopmentCards().get(cardType).isEmpty());
		}
		Assert.assertFalse(GameStatus.getInstance().getCurrentDices().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentTurnOrder().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentCouncilPalaceOrder().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentExcommunicatedPlayers().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentOwnLeaderCardsHand().isEmpty());
	}
}
