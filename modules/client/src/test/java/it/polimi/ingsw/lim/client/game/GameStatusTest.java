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
	private final Map<Integer, DevelopmentCardBuildingInformation> developmentCardsBuildingInformation = new HashMap<>();
	private final Map<Integer, DevelopmentCardCharacterInformation> developmentCardsCharacterInformation = new HashMap<>();
	private final Map<Integer, DevelopmentCardTerritoryInformation> developmentCardsTerritoryInformation = new HashMap<>();
	private final Map<Integer, DevelopmentCardVentureInformation> developmentCardsVentureInformation = new HashMap<>();
	private final Map<Integer, LeaderCardInformation> leaderCardsInformation = new HashMap<>();
	private final Map<Integer, ExcommunicationTileInformation> excommunicationTilesInformation = new HashMap<>();
	private final Map<Integer, PersonalBonusTileInformation> personalBonusTilesInformation = new HashMap<>();
	private final Map<Row, Integer> currentDevelopmentCardsBuilding = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsCharacter = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsTerritory = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsVenture = new EnumMap<>(Row.class);
	private final Map<FamilyMemberType, Integer> currentDices = new EnumMap<>(FamilyMemberType.class);
	private final Map<Integer, Integer> currentTurnOrder = new HashMap<>();
	private final Map<Integer, Integer> currentCouncilPalaceOrder = new HashMap<>();
	private final Map<Period, List<Integer>> currentExcommunicatedPlayers = new EnumMap<>(Period.class);

	@Before
	public void setUp()
	{
		this.developmentCardsBuildingInformation.put(0, new DevelopmentCardBuildingInformation(null, null, new ArrayList<>(), new RewardInformation(null, new ArrayList<>()), 0, new ArrayList<>()));
		this.developmentCardsCharacterInformation.put(0, new DevelopmentCardCharacterInformation(null, null, new ArrayList<>(), new RewardInformation(null, new ArrayList<>()), null));
		this.developmentCardsTerritoryInformation.put(0, new DevelopmentCardTerritoryInformation(null, null, new ArrayList<>(), new RewardInformation(null, new ArrayList<>()), 0, new ArrayList<>()));
		this.developmentCardsVentureInformation.put(0, new DevelopmentCardVentureInformation(null, null, new ArrayList<>(), new RewardInformation(null, new ArrayList<>()), 0));
		this.leaderCardsInformation.put(0, new LeaderCardModifierInformation(null, null, null, new ArrayList<>(), null));
		this.excommunicationTilesInformation.put(0, new ExcommunicationTileInformation(null, null));
		this.personalBonusTilesInformation.put(0, new PersonalBonusTileInformation(null, null, 0, new ArrayList<>(), 0, new ArrayList<>()));
		this.currentDevelopmentCardsBuilding.put(Row.FIRST, 0);
		this.currentDevelopmentCardsCharacter.put(Row.FIRST, 0);
		this.currentDevelopmentCardsTerritory.put(Row.FIRST, 0);
		this.currentDevelopmentCardsVenture.put(Row.FIRST, 0);
		this.currentDices.put(FamilyMemberType.BLACK, 0);
		this.currentTurnOrder.put(0, 0);
		this.currentCouncilPalaceOrder.put(0, 0);
		this.currentExcommunicatedPlayers.put(Period.FIRST, Collections.singletonList(0));
	}

	@Test
	public void testSetup()
	{
		GameStatus.getInstance().setup(this.developmentCardsBuildingInformation, this.developmentCardsCharacterInformation, this.developmentCardsTerritoryInformation, this.developmentCardsVentureInformation, this.leaderCardsInformation, this.excommunicationTilesInformation, this.personalBonusTilesInformation);
		Assert.assertFalse(GameStatus.getInstance().getDevelopmentCardsBuilding().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getDevelopmentCardsCharacter().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getDevelopmentCardsTerritory().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getDevelopmentCardsVenture().isEmpty());
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
		GameStatus.getInstance().updateGameStatus(new GameInformation(this.currentDevelopmentCardsBuilding, this.currentDevelopmentCardsCharacter, this.currentDevelopmentCardsTerritory, this.currentDevelopmentCardsVenture, this.currentDices, this.currentTurnOrder, this.currentCouncilPalaceOrder, this.currentExcommunicatedPlayers), Collections.singletonList(new PlayerInformation(0, 0, Collections.singletonList(0), Collections.singletonList(0), Collections.singletonList(0), Collections.singletonList(0), new HashMap<>(), 0, resourceAmounts, familyMemberTypesBoardPositions)), ownLeaderCardsHand);
		Assert.assertEquals(Row.FIRST, GameStatus.getInstance().getDevelopmentCardRow(CardType.BUILDING, 0));
		Assert.assertFalse(GameStatus.getInstance().getCurrentDevelopmentCardsBuilding().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentDevelopmentCardsCharacter().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentDevelopmentCardsTerritory().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentDevelopmentCardsVenture().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentDices().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentTurnOrder().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentCouncilPalaceOrder().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentExcommunicatedPlayers().isEmpty());
		Assert.assertFalse(GameStatus.getInstance().getCurrentOwnLeaderCardsHand().isEmpty());
	}
}
