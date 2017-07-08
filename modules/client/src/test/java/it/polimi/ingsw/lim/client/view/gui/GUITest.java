package it.polimi.ingsw.lim.client.view.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.actions.*;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformation;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformation;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.view.gui.CustomController;
import it.polimi.ingsw.lim.common.view.gui.JavaFXThreadingRule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class GUITest
{
	@Rule public JavaFXThreadingRule javaFxRule = new JavaFXThreadingRule();

	@Before
	public void setUp()
	{
		Client.setDebugger(Logger.getLogger(Client.class.getSimpleName().toUpperCase()));
		Client.getDebugger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new DebuggerFormatter());
		Client.getDebugger().addHandler(consoleHandler);
		Instance.setInstance(new Client());
		Client.getInstance().setCliStatus(CLIStatus.NONE);
		Client.getInstance().setInterfaceHandler(new InterfaceHandlerGUI());
		Map<CardType, Map<Integer, DevelopmentCardInformation>> developmentCardsInformation = new EnumMap<>(CardType.class);
		Map<Integer, DevelopmentCardInformation> developmentCardsBuildingInformation = new HashMap<>();
		developmentCardsBuildingInformation.put(0, new DevelopmentCardBuildingInformation("", "/images/development_cards/building/development_card_building_0.png", new ArrayList<>(), new RewardInformation("", new ArrayList<>()), 0, new ArrayList<>()));
		Map<Integer, DevelopmentCardInformation> developmentCardsCharacterInformation = new HashMap<>();
		developmentCardsCharacterInformation.put(0, new DevelopmentCardCharacterInformation("", "/images/development_cards/character/development_card_character_0.png", new ArrayList<>(), new RewardInformation("", new ArrayList<>()), null));
		Map<Integer, DevelopmentCardInformation> developmentCardsTerritoryInformation = new HashMap<>();
		developmentCardsTerritoryInformation.put(0, new DevelopmentCardTerritoryInformation("", "/images/development_cards/territory/development_card_territory_0.png", new ArrayList<>(), new RewardInformation("", new ArrayList<>()), 0, new ArrayList<>()));
		Map<Integer, DevelopmentCardInformation> developmentCardsVentureInformation = new HashMap<>();
		developmentCardsVentureInformation.put(0, new DevelopmentCardVentureInformation("", "/images/development_cards/venture/development_card_venture_0.png", new ArrayList<>(), new RewardInformation("", new ArrayList<>()), 0));
		developmentCardsInformation.put(CardType.BUILDING, developmentCardsBuildingInformation);
		developmentCardsInformation.put(CardType.CHARACTER, developmentCardsCharacterInformation);
		developmentCardsInformation.put(CardType.TERRITORY, developmentCardsTerritoryInformation);
		developmentCardsInformation.put(CardType.VENTURE, developmentCardsVentureInformation);
		Map<Integer, LeaderCardInformation> leaderCardsInformation = new HashMap<>();
		leaderCardsInformation.put(0, new LeaderCardModifierInformation("/images/leader_cards/leader_card_0.png", "", "", new ArrayList<>(), ""));
		Map<Integer, ExcommunicationTileInformation> excommunicationTilesInformation = new HashMap<>();
		excommunicationTilesInformation.put(0, new ExcommunicationTileInformation("/images/excommunication_tiles/excommunication_tile_1_1.png", ""));
		excommunicationTilesInformation.put(1, new ExcommunicationTileInformation("/images/excommunication_tiles/excommunication_tile_2_1.png", ""));
		excommunicationTilesInformation.put(2, new ExcommunicationTileInformation("/images/excommunication_tiles/excommunication_tile_3_1.png", ""));
		Map<Integer, PersonalBonusTileInformation> personalBonusTilesInformation = new HashMap<>();
		personalBonusTilesInformation.put(0, new PersonalBonusTileInformation("/images/personal_bonus_tiles/personal_bonus_tile_0.png", "/images/player_boards/player_board_0.png", 0, new ArrayList<>(), 0, new ArrayList<>()));
		GameStatus.getInstance().setup(developmentCardsInformation, leaderCardsInformation, excommunicationTilesInformation, personalBonusTilesInformation);
		Map<Period, Integer> currentExcommunicationTiles = new EnumMap<>(Period.class);
		currentExcommunicationTiles.put(Period.FIRST, 0);
		currentExcommunicationTiles.put(Period.SECOND, 1);
		currentExcommunicationTiles.put(Period.THIRD, 2);
		GameStatus.getInstance().setCurrentExcommunicationTiles(currentExcommunicationTiles);
		Map<Integer, List<ResourceAmount>> currentCouncilPrivilegeRewards = new HashMap<>();
		currentCouncilPrivilegeRewards.put(0, new ArrayList<>());
		GameStatus.getInstance().setCurrentCouncilPrivilegeRewards(currentCouncilPrivilegeRewards);
		Map<Integer, PlayerData> playersData = new HashMap<>();
		playersData.put(0, new PlayerData("test_username_1", Color.BLUE));
		playersData.put(1, new PlayerData("test_username_2", Color.GREEN));
		GameStatus.getInstance().setCurrentPlayerData(playersData);
		GameStatus.getInstance().setOwnPlayerIndex(0);
		Map<CardType, Map<Row, Integer>> currentDevelopmentCards = new EnumMap<>(CardType.class);
		for (CardType cardType : CardType.values()) {
			currentDevelopmentCards.put(cardType, new EnumMap<>(Row.class));
		}
		Map<FamilyMemberType, Integer> currentDices = new EnumMap<>(FamilyMemberType.class);
		currentDices.put(FamilyMemberType.BLACK, 1);
		currentDices.put(FamilyMemberType.ORANGE, 1);
		currentDices.put(FamilyMemberType.WHITE, 1);
		Map<Integer, Integer> currentTurnOrder = new HashMap<>();
		currentTurnOrder.put(0, 0);
		Map<Integer, Integer> currentCouncilPalaceOrder = new HashMap<>();
		currentCouncilPalaceOrder.put(0, 0);
		Map<Period, List<Integer>> currentExcommunicatedPlayers = new EnumMap<>(Period.class);
		currentExcommunicatedPlayers.put(Period.FIRST, Collections.singletonList(0));
		Map<ResourceType, Integer> resourceAmounts = new EnumMap<>(ResourceType.class);
		for (ResourceType resourceType : ResourceType.values()) {
			resourceAmounts.put(resourceType, 0);
		}
		Map<FamilyMemberType, BoardPosition> familyMemberTypesBoardPositions = new EnumMap<>(FamilyMemberType.class);
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			familyMemberTypesBoardPositions.put(familyMemberType, BoardPosition.NONE);
		}
		Map<CardType, List<Integer>> playerDevelopmentCardsInformation = new EnumMap<>(CardType.class);
		for (CardType cardType : CardType.values()) {
			playerDevelopmentCardsInformation.put(cardType, new ArrayList<>());
		}
		GameStatus.getInstance().updateGameStatus(new GameInformation(currentDevelopmentCards, currentDices, currentTurnOrder, currentCouncilPalaceOrder, currentExcommunicatedPlayers), Arrays.asList(new PlayerInformation(0, 0, playerDevelopmentCardsInformation, new HashMap<>(), 0, resourceAmounts, familyMemberTypesBoardPositions), new PlayerInformation(1, 0, playerDevelopmentCardsInformation, new HashMap<>(), 0, resourceAmounts, familyMemberTypesBoardPositions)), new HashMap<>());
		Map<ActionType, List<Serializable>> currentAvailableActions = new EnumMap<>(ActionType.class);
		for (ActionType actionType : ActionType.values()) {
			currentAvailableActions.put(actionType, new ArrayList<>());
		}
		List<Serializable> availableActions = new ArrayList<>();
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			availableActions.add(new AvailableActionFamilyMember(familyMemberType));
		}
		currentAvailableActions.put(ActionType.COUNCIL_PALACE, availableActions);
		currentAvailableActions.put(ActionType.HARVEST, availableActions);
		currentAvailableActions.put(ActionType.PRODUCTION_START, availableActions);
		List<Serializable> availableActionsMarket = new ArrayList<>();
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			for (MarketSlot marketSlot : MarketSlot.values()) {
				availableActionsMarket.add(new AvailableActionMarket(familyMemberType, marketSlot));
			}
		}
		currentAvailableActions.put(ActionType.MARKET, availableActionsMarket);
		List<Serializable> availableActionsPickDevelopmentCard = new ArrayList<>();
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			availableActionsPickDevelopmentCard.add(new AvailableActionPickDevelopmentCard(familyMemberType, CardType.BUILDING, Row.FIRST, new ArrayList<>(), new ArrayList<>()));
		}
		currentAvailableActions.put(ActionType.PICK_DEVELOPMENT_CARD, availableActionsPickDevelopmentCard);
		GameStatus.getInstance().setCurrentAvailableActions(currentAvailableActions);
		GameStatus.getInstance().setCurrentExpectedAction(new ExpectedAction(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER));
		Map<Integer, Integer> playersScores = new HashMap<>();
		playersScores.put(0, 0);
		playersScores.put(1, 0);
		GameStatus.getInstance().setPlayersScores(playersScores);
		GameStatus.getInstance().setPlayerIndexesVictoryPointsRecord(playersScores);
	}

	@Test
	public void testGui() throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(Utils.SCENE_CONNECTION));
		Parent parent = fxmlLoader.load();
		Stage newStage = new Stage();
		((CustomController) fxmlLoader.getController()).setStage(newStage);
		newStage.setScene(new Scene(parent));
		newStage.show();
		((ControllerConnection) fxmlLoader.getController()).setupGui();
		newStage.close();
		fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(Utils.SCENE_AUTHENTICATION));
		parent = fxmlLoader.load();
		newStage = new Stage();
		((CustomController) fxmlLoader.getController()).setStage(newStage);
		newStage.setScene(new Scene(parent));
		newStage.show();
		((ControllerAuthentication) fxmlLoader.getController()).setupGui();
		newStage.close();
		fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(Utils.SCENE_ROOM));
		parent = fxmlLoader.load();
		newStage = new Stage();
		((CustomController) fxmlLoader.getController()).setStage(newStage);
		newStage.setScene(new Scene(parent));
		newStage.show();
		((ControllerRoom) fxmlLoader.getController()).setupGui();
		newStage.close();
		fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(Utils.SCENE_GAME));
		parent = fxmlLoader.load();
		newStage = new Stage();
		((CustomController) fxmlLoader.getController()).setStage(newStage);
		newStage.setScene(new Scene(parent));
		newStage.show();
		((ControllerGame) fxmlLoader.getController()).setupGui();
		((ControllerGame) fxmlLoader.getController()).setOwnTurn();
		((ControllerGame) fxmlLoader.getController()).setOwnTurnExpectedAction();
		((ControllerGame) fxmlLoader.getController()).setOtherTurn();
		((ControllerGame) fxmlLoader.getController()).showPersonalBonusTiles();
		((ControllerGame) fxmlLoader.getController()).showLeaderCards();
		((ControllerGame) fxmlLoader.getController()).showExcommunicationOther();
		((ControllerGame) fxmlLoader.getController()).showExcommunication(Period.FIRST);
		((ControllerGame) fxmlLoader.getController()).showCouncilPalace(FamilyMemberType.BLACK);
		((ControllerGame) fxmlLoader.getController()).showHarvest(FamilyMemberType.BLACK);
		((ControllerGame) fxmlLoader.getController()).showMarket(MarketSlot.FIRST, FamilyMemberType.BLACK);
		((ControllerGame) fxmlLoader.getController()).showPickDevelopmentCard(FamilyMemberType.BLACK);
		((ControllerGame) fxmlLoader.getController()).showProductionStart(FamilyMemberType.BLACK);
		((ControllerGame) fxmlLoader.getController()).showLeaderActivate();
		((ControllerGame) fxmlLoader.getController()).showLeaderDiscard();
		((ControllerGame) fxmlLoader.getController()).showLeaderPlay();
		GameStatus.getInstance().setCurrentExpectedAction(new ExpectedActionChooseLorenzoDeMediciLeader(new HashMap<>()));
		((ControllerGame) fxmlLoader.getController()).showExpectedChooseLorenzoDeMediciLeader();
		GameStatus.getInstance().setCurrentExpectedAction(new ExpectedActionChooseRewardCouncilPrivilege(1));
		((ControllerGame) fxmlLoader.getController()).showExpectedChooseRewardCouncilPrivilege();
		GameStatus.getInstance().setCurrentExpectedAction(new ExpectedActionChooseRewardHarvest(0));
		((ControllerGame) fxmlLoader.getController()).showExpectedChooseRewardHarvest();
		GameStatus.getInstance().setCurrentExpectedAction(new ExpectedActionChooseRewardPickDevelopmentCard(new ArrayList<>()));
		((ControllerGame) fxmlLoader.getController()).showExpectedChooseRewardPickDevelopmentCard();
		GameStatus.getInstance().setCurrentExpectedAction(new ExpectedActionChooseRewardProductionStart(0));
		((ControllerGame) fxmlLoader.getController()).showExpectedChooseRewardProductionStart();
		GameStatus.getInstance().setCurrentExpectedAction(new ExpectedAction(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER));
		((ControllerGame) fxmlLoader.getController()).showExpectedChooseRewardTemporaryModifier();
		GameStatus.getInstance().setCurrentExpectedAction(new ExpectedActionProductionTrade(new HashMap<>()));
		((ControllerGame) fxmlLoader.getController()).showExpectedProductionTrade();
		((ControllerGame) fxmlLoader.getController()).showEndGame();
		newStage.close();
	}

	@After
	public void tearDown()
	{
		Client.getInstance().stop();
	}
}
