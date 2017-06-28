package it.polimi.ingsw.lim.client.game;

import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;

import java.util.*;
import java.util.Map.Entry;

public class GameStatus
{
	private static final GameStatus INSTANCE = new GameStatus();
	private final Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuilding = new HashMap<>();
	private final Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacter = new HashMap<>();
	private final Map<Integer, DevelopmentCardTerritoryInformations> developmentCardsTerritory = new HashMap<>();
	private final Map<Integer, DevelopmentCardVentureInformations> developmentCardsVenture = new HashMap<>();
	private final Map<Integer, LeaderCardInformations> leaderCards = new HashMap<>();
	private final Map<Integer, ExcommunicationTileInformations> excommunicationTiles = new HashMap<>();
	private final Map<Integer, CouncilPalaceRewardInformations> councilPalaceRewards = new HashMap<>();
	private final Map<Integer, PersonalBonusTileInformations> personalBonusTiles = new HashMap<>();
	private final Map<Period, Integer> currentExcommunicationTiles = new EnumMap<>(Period.class);
	private final Map<Integer, PlayerData> currentPlayerData = new HashMap<>();
	private int ownPlayerIndex;
	private final Map<Row, Integer> currentDevelopmentCardsBuilding = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsCharacter = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsTerritory = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsVenture = new EnumMap<>(Row.class);
	private final Map<CardType, Map<Row, Integer>> currentDevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<FamilyMemberType, Integer> currentDices = new EnumMap<>(FamilyMemberType.class);
	private final Map<Integer, Integer> currentTurnOrder = new HashMap<>();
	private final Map<Integer, Integer> currentCouncilPalaceOrder = new HashMap<>();
	private final Map<Period, List<Integer>> currentExcommunicatedPlayers = new EnumMap<>(Period.class);
	private final Map<Integer, Boolean> currentOwnLeaderCardsHand = new HashMap<>();
	private int currentTurnPlayerIndex = -1;
	private final Map<ActionType, List<AvailableAction>> currentAvailableActions = new EnumMap<>(ActionType.class);
	private final List<Integer> availablePersonalBonusTiles = new ArrayList<>();
	private final List<Integer> availableLeaderCards = new ArrayList<>();

	private GameStatus()
	{
		this.currentDevelopmentCards.put(CardType.BUILDING, this.currentDevelopmentCardsBuilding);
		this.currentDevelopmentCards.put(CardType.CHARACTER, this.currentDevelopmentCardsCharacter);
		this.currentDevelopmentCards.put(CardType.TERRITORY, this.currentDevelopmentCardsTerritory);
		this.currentDevelopmentCards.put(CardType.VENTURE, this.currentDevelopmentCardsVenture);
	}

	public void setup(Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuilding, Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacter, Map<Integer, DevelopmentCardTerritoryInformations> developmentCardsTerritory, Map<Integer, DevelopmentCardVentureInformations> developmentCardsVenture, Map<Integer, LeaderCardInformations> leaderCards, Map<Integer, ExcommunicationTileInformations> excommunicationTiles, Map<Integer, CouncilPalaceRewardInformations> councilPalaceRewards, Map<Integer, PersonalBonusTileInformations> personalBonusTiles)
	{
		this.developmentCardsBuilding.clear();
		this.developmentCardsBuilding.putAll(developmentCardsBuilding);
		this.developmentCardsCharacter.clear();
		this.developmentCardsCharacter.putAll(developmentCardsCharacter);
		this.developmentCardsTerritory.clear();
		this.developmentCardsTerritory.putAll(developmentCardsTerritory);
		this.developmentCardsVenture.clear();
		this.developmentCardsVenture.putAll(developmentCardsVenture);
		this.leaderCards.clear();
		this.leaderCards.putAll(leaderCards);
		this.excommunicationTiles.clear();
		this.excommunicationTiles.putAll(excommunicationTiles);
		this.councilPalaceRewards.clear();
		this.councilPalaceRewards.putAll(councilPalaceRewards);
		this.personalBonusTiles.clear();
		this.personalBonusTiles.putAll(personalBonusTiles);
	}

	public void updateGameStatus(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand)
	{
		this.setCurrentDevelopmentCardsBuilding(gameInformations.getDevelopmentCardsBuilding());
		this.setCurrentDevelopmentCardsCharacter(gameInformations.getDevelopmentCardsCharacter());
		this.setCurrentDevelopmentCardsTerritory(gameInformations.getDevelopmentCardsTerritory());
		this.setCurrentDevelopmentCardsVenture(gameInformations.getDevelopmentCardsVenture());
		this.setCurrentDices(gameInformations.getDices());
		this.setCurrentTurnOrder(gameInformations.getTurnOrder());
		this.setCurrentCouncilPalaceOrder(gameInformations.getCouncilPalaceOrder());
		this.setCurrentExcommunicatedPlayers(gameInformations.getExcommunicatedPlayers());
		this.setCurrentOwnLeaderCardsHand(ownLeaderCardsHand);
		for (PlayerInformations playerInformations : playersInformations) {
			if (this.currentPlayerData.get(playerInformations.getIndex()) != null) {
				this.currentPlayerData.get(playerInformations.getIndex()).setPersonalBonusTile(playerInformations.getPersonalBonusTile());
				this.currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsBuilding(playerInformations.getDevelopmentCardsBuilding());
				this.currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsCharacter(playerInformations.getDevelopmentCardsCharacter());
				this.currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsTerritory(playerInformations.getDevelopmentCardsTerritory());
				this.currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsVenture(playerInformations.getDevelopmentCardsVenture());
				this.currentPlayerData.get(playerInformations.getIndex()).setLeaderCardsPlayed(playerInformations.getLeaderCardsPlayed());
				this.currentPlayerData.get(playerInformations.getIndex()).setLeaderCardsInHandNumber(playerInformations.getLeaderCardsInHandNumber());
				this.currentPlayerData.get(playerInformations.getIndex()).setResourceAmounts(playerInformations.getResourceAmounts());
				this.currentPlayerData.get(playerInformations.getIndex()).setFamilyMembersPositions(playerInformations.getFamilyMembersPositions());
			}
		}
	}

	public Row getDevelopmentCardRow(CardType cardType, int index)
	{
		for (Entry<Row, Integer> developmentCard : this.currentDevelopmentCards.get(cardType).entrySet()) {
			if (developmentCard.getValue() == index) {
				return developmentCard.getKey();
			}
		}
		return null;
	}

	public static GameStatus getInstance()
	{
		return GameStatus.INSTANCE;
	}

	public Map<Integer, DevelopmentCardBuildingInformations> getDevelopmentCardsBuilding()
	{
		return this.developmentCardsBuilding;
	}

	public Map<Integer, DevelopmentCardCharacterInformations> getDevelopmentCardsCharacter()
	{
		return this.developmentCardsCharacter;
	}

	public Map<Integer, DevelopmentCardTerritoryInformations> getDevelopmentCardsTerritory()
	{
		return this.developmentCardsTerritory;
	}

	public Map<Integer, DevelopmentCardVentureInformations> getDevelopmentCardsVenture()
	{
		return this.developmentCardsVenture;
	}

	public Map<Integer, LeaderCardInformations> getLeaderCards()
	{
		return this.leaderCards;
	}

	public Map<Integer, ExcommunicationTileInformations> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public Map<Integer, CouncilPalaceRewardInformations> getCouncilPalaceRewards()
	{
		return this.councilPalaceRewards;
	}

	public Map<Integer, PersonalBonusTileInformations> getPersonalBonusTiles()
	{
		return this.personalBonusTiles;
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

	public Map<FamilyMemberType, Integer> getCurrentDices()
	{
		return this.currentDices;
	}

	private void setCurrentDices(Map<FamilyMemberType, Integer> currentDices)
	{
		this.currentDices.clear();
		this.currentDices.putAll(currentDices);
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

	public Map<Period, List<Integer>> getCurrentExcommunicatedPlayers()
	{
		return this.currentExcommunicatedPlayers;
	}

	private void setCurrentExcommunicatedPlayers(Map<Period, List<Integer>> currentExcommunicatedPlayers)
	{
		this.currentExcommunicatedPlayers.clear();
		this.currentExcommunicatedPlayers.putAll(currentExcommunicatedPlayers);
	}

	public Map<Integer, Boolean> getCurrentOwnLeaderCardsHand()
	{
		return this.currentOwnLeaderCardsHand;
	}

	private void setCurrentOwnLeaderCardsHand(Map<Integer, Boolean> currentOwnLeaderCardsHand)
	{
		this.currentOwnLeaderCardsHand.clear();
		this.currentOwnLeaderCardsHand.putAll(currentOwnLeaderCardsHand);
	}

	public int getCurrentTurnPlayerIndex()
	{
		return this.currentTurnPlayerIndex;
	}

	public void setCurrentTurnPlayerIndex(int currentTurnPlayerIndex)
	{
		this.currentTurnPlayerIndex = currentTurnPlayerIndex;
	}

	public Map<ActionType, List<AvailableAction>> getCurrentAvailableActions()
	{
		return this.currentAvailableActions;
	}

	public void setCurrentAvailableActions(Map<ActionType, List<AvailableAction>> currentAvailableActions)
	{
		this.currentAvailableActions.clear();
		this.currentAvailableActions.putAll(currentAvailableActions);
	}

	public List<Integer> getAvailablePersonalBonusTiles()
	{
		return this.availablePersonalBonusTiles;
	}

	public void setAvailablePersonalBonusTiles(List<Integer> availablePersonalBonusTiles)
	{
		this.availablePersonalBonusTiles.clear();
		this.availablePersonalBonusTiles.addAll(availablePersonalBonusTiles);
	}

	public List<Integer> getAvailableLeaderCards()
	{
		return this.availableLeaderCards;
	}

	public void setAvailableLeaderCards(List<Integer> availableLeaderCards)
	{
		this.availableLeaderCards.clear();
		this.availableLeaderCards.addAll(availableLeaderCards);
	}
}
