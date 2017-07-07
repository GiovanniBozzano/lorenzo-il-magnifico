package it.polimi.ingsw.lim.client.game;

import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformation;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformation;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformation;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformation;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

public class GameStatus
{
	private static final GameStatus INSTANCE = new GameStatus();
	private final Map<CardType, Map<Integer, DevelopmentCardInformation>> developmentCards = new EnumMap<>(CardType.class);
	private final Map<Integer, LeaderCardInformation> leaderCards = new HashMap<>();
	private final Map<Integer, ExcommunicationTileInformation> excommunicationTiles = new HashMap<>();
	private final Map<Integer, PersonalBonusTileInformation> personalBonusTiles = new HashMap<>();
	private final Map<Period, Integer> currentExcommunicationTiles = new EnumMap<>(Period.class);
	private final Map<Integer, List<ResourceAmount>> currentCouncilPrivilegeRewards = new HashMap<>();
	private final Map<Integer, PlayerData> currentPlayerData = new HashMap<>();
	private final Map<CardType, Map<Row, Integer>> currentDevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<FamilyMemberType, Integer> currentDices = new EnumMap<>(FamilyMemberType.class);
	private final Map<Integer, Integer> currentTurnOrder = new HashMap<>();
	private final Map<Integer, Integer> currentCouncilPalaceOrder = new HashMap<>();
	private final Map<Period, List<Integer>> currentExcommunicatedPlayers = new EnumMap<>(Period.class);
	private final Map<Integer, Boolean> currentOwnLeaderCardsHand = new HashMap<>();
	private final Map<ActionType, List<Serializable>> currentAvailableActions = new EnumMap<>(ActionType.class);
	private final List<Integer> availablePersonalBonusTiles = new ArrayList<>();
	private final List<Integer> availableLeaderCards = new ArrayList<>();
	private final Map<Integer, Integer> playersScores = new HashMap<>();
	private final Map<Integer, Integer> playerIndexesVictoryPointsRecord = new HashMap<>();
	private int ownPlayerIndex;
	private int currentTurnPlayerIndex = -1;
	private ExpectedAction currentExpectedAction;

	private GameStatus()
	{
		this.developmentCards.put(CardType.BUILDING, new HashMap<>());
		this.developmentCards.put(CardType.CHARACTER, new HashMap<>());
		this.developmentCards.put(CardType.TERRITORY, new HashMap<>());
		this.developmentCards.put(CardType.VENTURE, new HashMap<>());
		this.currentDevelopmentCards.put(CardType.BUILDING, new EnumMap<>(Row.class));
		this.currentDevelopmentCards.put(CardType.CHARACTER, new EnumMap<>(Row.class));
		this.currentDevelopmentCards.put(CardType.TERRITORY, new EnumMap<>(Row.class));
		this.currentDevelopmentCards.put(CardType.VENTURE, new EnumMap<>(Row.class));
	}

	public void setup(Map<CardType, Map<Integer, DevelopmentCardInformation>> developmentCardsInformation, Map<Integer, LeaderCardInformation> leaderCards, Map<Integer, ExcommunicationTileInformation> excommunicationTiles, Map<Integer, PersonalBonusTileInformation> personalBonusTiles)
	{
		for (Entry<CardType, Map<Integer, DevelopmentCardInformation>> developmentCardsInformationType : developmentCardsInformation.entrySet()) {
			this.setDevelopmentCards(developmentCardsInformationType.getKey(), developmentCardsInformationType.getValue());
		}
		this.setLeaderCards(leaderCards);
		this.setExcommunicationTiles(excommunicationTiles);
		this.setPersonalBonusTiles(personalBonusTiles);
	}

	public void updateGameStatus(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand)
	{
		this.setCurrentDevelopmentCards(CardType.BUILDING, gameInformation.getDevelopmentCardsBuilding());
		this.setCurrentDevelopmentCards(CardType.CHARACTER, gameInformation.getDevelopmentCardsCharacter());
		this.setCurrentDevelopmentCards(CardType.TERRITORY, gameInformation.getDevelopmentCardsTerritory());
		this.setCurrentDevelopmentCards(CardType.VENTURE, gameInformation.getDevelopmentCardsVenture());
		this.setCurrentDices(gameInformation.getDices());
		this.setCurrentTurnOrder(gameInformation.getTurnOrder());
		this.setCurrentCouncilPalaceOrder(gameInformation.getCouncilPalaceOrder());
		this.setCurrentExcommunicatedPlayers(gameInformation.getExcommunicatedPlayers());
		this.setCurrentOwnLeaderCardsHand(ownLeaderCardsHand);
		for (PlayerInformation playerInformation : playersInformation) {
			if (this.currentPlayerData.get(playerInformation.getIndex()) != null) {
				this.currentPlayerData.get(playerInformation.getIndex()).setPersonalBonusTile(playerInformation.getPersonalBonusTile());
				this.currentPlayerData.get(playerInformation.getIndex()).setDevelopmentCards(CardType.BUILDING, playerInformation.getDevelopmentCardsBuilding());
				this.currentPlayerData.get(playerInformation.getIndex()).setDevelopmentCards(CardType.CHARACTER, playerInformation.getDevelopmentCardsCharacter());
				this.currentPlayerData.get(playerInformation.getIndex()).setDevelopmentCards(CardType.TERRITORY, playerInformation.getDevelopmentCardsTerritory());
				this.currentPlayerData.get(playerInformation.getIndex()).setDevelopmentCards(CardType.VENTURE, playerInformation.getDevelopmentCardsVenture());
				this.currentPlayerData.get(playerInformation.getIndex()).setLeaderCardsPlayed(playerInformation.getLeaderCardsPlayed());
				this.currentPlayerData.get(playerInformation.getIndex()).setLeaderCardsInHandNumber(playerInformation.getLeaderCardsInHandNumber());
				this.currentPlayerData.get(playerInformation.getIndex()).setResourceAmounts(playerInformation.getResourceAmounts());
				this.currentPlayerData.get(playerInformation.getIndex()).setFamilyMemberTypesPositions(playerInformation.getFamilyMembersPositions());
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
		throw new NoSuchElementException();
	}

	public void setDevelopmentCards(CardType cardType, Map<Integer, DevelopmentCardInformation> developmentCards)
	{
		this.developmentCards.get(cardType).clear();
		this.developmentCards.get(cardType).putAll(developmentCards);
	}

	private void setCurrentDevelopmentCards(CardType cardType, Map<Row, Integer> currentDevelopmentCards)
	{
		this.currentDevelopmentCards.get(cardType).clear();
		this.currentDevelopmentCards.get(cardType).putAll(currentDevelopmentCards);
	}

	public static GameStatus getInstance()
	{
		return GameStatus.INSTANCE;
	}

	public Map<CardType, Map<Integer, DevelopmentCardInformation>> getDevelopmentCards()
	{
		return this.developmentCards;
	}

	public Map<Integer, LeaderCardInformation> getLeaderCards()
	{
		return this.leaderCards;
	}

	public void setLeaderCards(Map<Integer, LeaderCardInformation> leaderCards)
	{
		this.leaderCards.clear();
		this.leaderCards.putAll(leaderCards);
	}

	public Map<Integer, ExcommunicationTileInformation> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public void setExcommunicationTiles(Map<Integer, ExcommunicationTileInformation> excommunicationTiles)
	{
		this.excommunicationTiles.clear();
		this.excommunicationTiles.putAll(excommunicationTiles);
	}

	public Map<Integer, PersonalBonusTileInformation> getPersonalBonusTiles()
	{
		return this.personalBonusTiles;
	}

	public void setPersonalBonusTiles(Map<Integer, PersonalBonusTileInformation> personalBonusTiles)
	{
		this.personalBonusTiles.clear();
		this.personalBonusTiles.putAll(personalBonusTiles);
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

	public Map<Integer, List<ResourceAmount>> getCurrentCouncilPrivilegeRewards()
	{
		return this.currentCouncilPrivilegeRewards;
	}

	public void setCurrentCouncilPrivilegeRewards(Map<Integer, List<ResourceAmount>> currentCouncilPrivilegeRewards)
	{
		this.currentCouncilPrivilegeRewards.clear();
		this.currentCouncilPrivilegeRewards.putAll(currentCouncilPrivilegeRewards);
	}

	public Map<Integer, PlayerData> getCurrentPlayersData()
	{
		return this.currentPlayerData;
	}

	public int getOwnPlayerIndex()
	{
		return this.ownPlayerIndex;
	}

	public void setOwnPlayerIndex(int ownPlayerIndex)
	{
		this.ownPlayerIndex = ownPlayerIndex;
	}

	public Map<CardType, Map<Row, Integer>> getCurrentDevelopmentCards()
	{
		return this.currentDevelopmentCards;
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

	public Map<ActionType, List<Serializable>> getCurrentAvailableActions()
	{
		return this.currentAvailableActions;
	}

	public void setCurrentAvailableActions(Map<ActionType, List<Serializable>> currentAvailableActions)
	{
		this.currentAvailableActions.clear();
		this.currentAvailableActions.putAll(currentAvailableActions);
	}

	public ExpectedAction getCurrentExpectedAction()
	{
		return this.currentExpectedAction;
	}

	public void setCurrentExpectedAction(ExpectedAction currentExpectedAction)
	{
		this.currentExpectedAction = currentExpectedAction;
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

	public Map<Integer, Integer> getPlayersScores()
	{
		return this.playersScores;
	}

	public void setPlayersScores(Map<Integer, Integer> playersScores)
	{
		this.playersScores.clear();
		this.playersScores.putAll(playersScores);
	}

	public Map<Integer, Integer> getPlayerIndexesVictoryPointsRecord()
	{
		return this.playerIndexesVictoryPointsRecord;
	}

	public void setPlayerIndexesVictoryPointsRecord(Map<Integer, Integer> playerIndexesVictoryPointsRecord)
	{
		this.playerIndexesVictoryPointsRecord.clear();
		this.playerIndexesVictoryPointsRecord.putAll(playerIndexesVictoryPointsRecord);
	}

	public void setCurrentPlayerData(Map<Integer, PlayerData> currentPlayerData)
	{
		this.currentPlayerData.clear();
		this.currentPlayerData.putAll(currentPlayerData);
	}
}
