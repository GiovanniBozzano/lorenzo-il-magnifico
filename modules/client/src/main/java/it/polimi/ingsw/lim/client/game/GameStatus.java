package it.polimi.ingsw.lim.client.game;

import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformations;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;

import java.util.*;

public class GameStatus
{
	private static final GameStatus INSTANCE = new GameStatus();
	private final List<DevelopmentCardInformations> developmentCards = new ArrayList<>();
	private final List<LeaderCardInformations> leaderCards = new ArrayList<>();
	private final List<ExcommunicationTileInformations> excommunicationTiles = new ArrayList<>();
	private final List<CouncilPalaceRewardInformations> councilPalaceRewards = new ArrayList<>();
	private final Map<Period, Integer> currentExcommunicationTiles = new EnumMap<>(Period.class);
	private final Map<Integer, PlayerData> currentPlayerData = new HashMap<>();
	private int ownPlayerIndex;
	private final Map<Row, Integer> currentDevelopmentCardsBuilding = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsCharacter = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsTerritory = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsVenture = new EnumMap<>(Row.class);
	private final Map<Integer, Integer> currentTurnOrder = new HashMap<>();
	private final Map<Integer, Integer> currentCouncilPalaceOrder = new HashMap<>();
	private int currentTurnPlayerIndex;
	private final List<AvailableAction> currentAvailableActions = new ArrayList<>();

	private GameStatus()
	{
	}

	public void setup(List<DevelopmentCardInformations> developmentCards, List<LeaderCardInformations> leaderCards, List<ExcommunicationTileInformations> excommunicationTiles, List<CouncilPalaceRewardInformations> councilPalaceRewards)
	{
		this.developmentCards.clear();
		this.developmentCards.addAll(developmentCards);
		this.leaderCards.clear();
		this.leaderCards.addAll(leaderCards);
		this.excommunicationTiles.clear();
		this.excommunicationTiles.addAll(excommunicationTiles);
		this.councilPalaceRewards.clear();
		this.councilPalaceRewards.addAll(councilPalaceRewards);
	}

	public static void updateGameStatus(GameInformations gameInformations, List<PlayerInformations> playersInformations)
	{
		GameStatus.getInstance().setCurrentDevelopmentCardsBuilding(gameInformations.getDevelopmentCardsBuilding());
		GameStatus.getInstance().setCurrentDevelopmentCardsCharacter(gameInformations.getDevelopmentCardsCharacter());
		GameStatus.getInstance().setCurrentDevelopmentCardsTerritory(gameInformations.getDevelopmentCardsTerritory());
		GameStatus.getInstance().setCurrentDevelopmentCardsVenture(gameInformations.getDevelopmentCardsVenture());
		GameStatus.getInstance().setCurrentTurnOrder(gameInformations.getTurnOrder());
		GameStatus.getInstance().setCurrentCouncilPalaceOrder(gameInformations.getCouncilPalaceOrder());
		for (PlayerInformations playerInformations : playersInformations) {
			if (GameStatus.getInstance().currentPlayerData.get(playerInformations.getIndex()) != null) {
				GameStatus.getInstance().currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsBuilding(playerInformations.getDevelopmentCardsBuilding());
				GameStatus.getInstance().currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsCharacter(playerInformations.getDevelopmentCardsCharacter());
				GameStatus.getInstance().currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsTerritory(playerInformations.getDevelopmentCardsTerritory());
				GameStatus.getInstance().currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsVenture(playerInformations.getDevelopmentCardsVenture());
				GameStatus.getInstance().currentPlayerData.get(playerInformations.getIndex()).setLeaderCardsStatuses(playerInformations.getLeaderCardsStatuses());
				GameStatus.getInstance().currentPlayerData.get(playerInformations.getIndex()).setResourceAmounts(playerInformations.getResourceAmounts());
				GameStatus.getInstance().currentPlayerData.get(playerInformations.getIndex()).setFamilyMembersPositions(playerInformations.getFamilyMembersPositions());
			}
		}
	}

	public static GameStatus getInstance()
	{
		return GameStatus.INSTANCE;
	}

	public List<DevelopmentCardInformations> getDevelopmentCards()
	{
		return this.developmentCards;
	}

	public List<LeaderCardInformations> getLeaderCards()
	{
		return this.leaderCards;
	}

	public List<ExcommunicationTileInformations> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public List<CouncilPalaceRewardInformations> getCouncilPalaceRewards()
	{
		return this.councilPalaceRewards;
	}

	public Map<Period, Integer> getCurrentExcommunicationTiles()
	{
		return this.currentExcommunicationTiles;
	}

	public void setCurrentExcommunicationTiles(Map<Period, Integer> currentExcommunicationTiles)
	{
		this.currentExcommunicationTiles.clear();
		this.currentExcommunicationTiles.putAll(currentExcommunicationTiles);
	}

	public Map<Integer, PlayerData> getCurrentPlayersData()
	{
		return this.currentPlayerData;
	}

	public void setCurrentPlayerData(Map<Integer, PlayerData> currentPlayerData)
	{
		this.currentPlayerData.clear();
		this.currentPlayerData.putAll(currentPlayerData);
	}

	public int getOwnPlayerIndex()
	{
		return this.ownPlayerIndex;
	}

	public void setOwnPlayerIndex(int ownPlayerIndex)
	{
		this.ownPlayerIndex = ownPlayerIndex;
	}

	public Map<Row, Integer> getCurrentDevelopmentCardsBuilding()
	{
		return this.currentDevelopmentCardsBuilding;
	}

	private void setCurrentDevelopmentCardsBuilding(Map<Row, Integer> currentDevelopmentCardsBuilding)
	{
		this.currentDevelopmentCardsBuilding.clear();
		this.currentDevelopmentCardsBuilding.putAll(currentDevelopmentCardsBuilding);
	}

	public Map<Row, Integer> getCurrentDevelopmentCardsCharacter()
	{
		return this.currentDevelopmentCardsCharacter;
	}

	private void setCurrentDevelopmentCardsCharacter(Map<Row, Integer> currentDevelopmentCardsCharacter)
	{
		this.currentDevelopmentCardsCharacter.clear();
		this.currentDevelopmentCardsCharacter.putAll(currentDevelopmentCardsCharacter);
	}

	public Map<Row, Integer> getCurrentDevelopmentCardsTerritory()
	{
		return this.currentDevelopmentCardsTerritory;
	}

	private void setCurrentDevelopmentCardsTerritory(Map<Row, Integer> currentDevelopmentCardsTerritory)
	{
		this.currentDevelopmentCardsTerritory.clear();
		this.currentDevelopmentCardsTerritory.putAll(currentDevelopmentCardsTerritory);
	}

	public Map<Row, Integer> getCurrentDevelopmentCardsVenture()
	{
		return this.currentDevelopmentCardsVenture;
	}

	private void setCurrentDevelopmentCardsVenture(Map<Row, Integer> currentDevelopmentCardsVenture)
	{
		this.currentDevelopmentCardsVenture.clear();
		this.currentDevelopmentCardsVenture.putAll(currentDevelopmentCardsVenture);
	}

	public Map<Integer, Integer> getCurrentTurnOrder()
	{
		return this.currentTurnOrder;
	}

	private void setCurrentTurnOrder(Map<Integer, Integer> currentTurnOrder)
	{
		this.currentTurnOrder.clear();
		this.currentTurnOrder.putAll(currentTurnOrder);
	}

	public Map<Integer, Integer> getCurrentCouncilPalaceOrder()
	{
		return this.currentCouncilPalaceOrder;
	}

	private void setCurrentCouncilPalaceOrder(Map<Integer, Integer> currentCouncilPalaceOrder)
	{
		this.currentCouncilPalaceOrder.clear();
		this.currentCouncilPalaceOrder.putAll(currentCouncilPalaceOrder);
	}

	public int getCurrentTurnPlayerIndex()
	{
		return this.currentTurnPlayerIndex;
	}

	public void setCurrentTurnPlayerIndex(int currentTurnPlayerIndex)
	{
		this.currentTurnPlayerIndex = currentTurnPlayerIndex;
	}

	public List<AvailableAction> getCurrentAvailableActions()
	{
		return this.currentAvailableActions;
	}

	public void setCurrentAvailableActions(List<AvailableAction> currentAvailableActions)
	{
		this.currentAvailableActions.clear();
		this.currentAvailableActions.addAll(currentAvailableActions);
	}
}
