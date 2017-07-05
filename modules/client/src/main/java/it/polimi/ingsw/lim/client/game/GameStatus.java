package it.polimi.ingsw.lim.client.game;

import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformation;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformation;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

public class GameStatus
{
	private static final GameStatus INSTANCE = new GameStatus();
	private final Map<Integer, DevelopmentCardInformation> developmentCardsBuilding = new HashMap<>();
	private final Map<Integer, DevelopmentCardInformation> developmentCardsCharacter = new HashMap<>();
	private final Map<Integer, DevelopmentCardInformation> developmentCardsTerritory = new HashMap<>();
	private final Map<Integer, DevelopmentCardInformation> developmentCardsVenture = new HashMap<>();
	private final Map<CardType, Map<Integer, DevelopmentCardInformation>> developmentCards = new EnumMap<>(CardType.class);
	private final Map<Integer, LeaderCardInformation> leaderCards = new HashMap<>();
	private final Map<Integer, ExcommunicationTileInformation> excommunicationTiles = new HashMap<>();
	private final Map<Integer, PersonalBonusTileInformation> personalBonusTiles = new HashMap<>();
	private final Map<Period, Integer> currentExcommunicationTiles = new EnumMap<>(Period.class);
	private final Map<Integer, List<ResourceAmount>> currentCouncilPrivilegeRewards = new HashMap<>();
	private final Map<Integer, PlayerData> currentPlayerData = new HashMap<>();
	private final Map<Row, Integer> currentDevelopmentCardsBuilding = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsCharacter = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsTerritory = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsVenture = new EnumMap<>(Row.class);
	private final Map<CardType, Map<Row, Integer>> currentDevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<FamilyMemberType, Integer> currentDices = new EnumMap<>(FamilyMemberType.class);
	private final Map<Integer, Integer> currentTurnOrder = new HashMap<>();
	private final Map<Integer, Integer> currentCouncilPalaceOrder = new HashMap<>();
	private final Map<Period, List<Integer>> currentExcommunicatedPlayers = new EnumMap<>(Period.class);
	// currentOwnLeaderCardsHand boolean = true if the card can be played
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
		this.developmentCards.put(CardType.BUILDING, this.developmentCardsBuilding);
		this.developmentCards.put(CardType.CHARACTER, this.developmentCardsCharacter);
		this.developmentCards.put(CardType.TERRITORY, this.developmentCardsTerritory);
		this.developmentCards.put(CardType.VENTURE, this.developmentCardsVenture);
		this.currentDevelopmentCards.put(CardType.BUILDING, this.currentDevelopmentCardsBuilding);
		this.currentDevelopmentCards.put(CardType.CHARACTER, this.currentDevelopmentCardsCharacter);
		this.currentDevelopmentCards.put(CardType.TERRITORY, this.currentDevelopmentCardsTerritory);
		this.currentDevelopmentCards.put(CardType.VENTURE, this.currentDevelopmentCardsVenture);
	}

	public void setup(Map<Integer, DevelopmentCardBuildingInformation> developmentCardsBuilding, Map<Integer, DevelopmentCardCharacterInformation> developmentCardsCharacter, Map<Integer, DevelopmentCardTerritoryInformation> developmentCardsTerritory, Map<Integer, DevelopmentCardVentureInformation> developmentCardsVenture, Map<Integer, LeaderCardInformation> leaderCards, Map<Integer, ExcommunicationTileInformation> excommunicationTiles, Map<Integer, PersonalBonusTileInformation> personalBonusTiles)
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
		this.personalBonusTiles.clear();
		this.personalBonusTiles.putAll(personalBonusTiles);
	}

	public void updateGameStatus(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand)
	{
		this.setCurrentDevelopmentCardsBuilding(gameInformation.getDevelopmentCardsBuilding());
		this.setCurrentDevelopmentCardsCharacter(gameInformation.getDevelopmentCardsCharacter());
		this.setCurrentDevelopmentCardsTerritory(gameInformation.getDevelopmentCardsTerritory());
		this.setCurrentDevelopmentCardsVenture(gameInformation.getDevelopmentCardsVenture());
		this.setCurrentDices(gameInformation.getDices());
		this.setCurrentTurnOrder(gameInformation.getTurnOrder());
		this.setCurrentCouncilPalaceOrder(gameInformation.getCouncilPalaceOrder());
		this.setCurrentExcommunicatedPlayers(gameInformation.getExcommunicatedPlayers());
		this.setCurrentOwnLeaderCardsHand(ownLeaderCardsHand);
		for (PlayerInformation playerInformation : playersInformation) {
			if (this.currentPlayerData.get(playerInformation.getIndex()) != null) {
				this.currentPlayerData.get(playerInformation.getIndex()).setPersonalBonusTile(playerInformation.getPersonalBonusTile());
				this.currentPlayerData.get(playerInformation.getIndex()).setDevelopmentCardsBuilding(playerInformation.getDevelopmentCardsBuilding());
				this.currentPlayerData.get(playerInformation.getIndex()).setDevelopmentCardsCharacter(playerInformation.getDevelopmentCardsCharacter());
				this.currentPlayerData.get(playerInformation.getIndex()).setDevelopmentCardsTerritory(playerInformation.getDevelopmentCardsTerritory());
				this.currentPlayerData.get(playerInformation.getIndex()).setDevelopmentCardsVenture(playerInformation.getDevelopmentCardsVenture());
				this.currentPlayerData.get(playerInformation.getIndex()).setLeaderCardsPlayed(playerInformation.getLeaderCardsPlayed());
				this.currentPlayerData.get(playerInformation.getIndex()).setLeaderCardsInHandNumber(playerInformation.getLeaderCardsInHandNumber());
				this.currentPlayerData.get(playerInformation.getIndex()).setResourceAmounts(playerInformation.getResourceAmounts());
				this.currentPlayerData.get(playerInformation.getIndex()).setFamilyMembersPositions(playerInformation.getFamilyMembersPositions());
			}
		}
	}

	public Row getDevelopmentCardRow(CardType cardType, int index) throws NoSuchElementException
	{
		for (Entry<Row, Integer> developmentCard : this.currentDevelopmentCards.get(cardType).entrySet()) {
			if (developmentCard.getValue() == index) {
				return developmentCard.getKey();
			}
		}
		throw new NoSuchElementException();
	}

	public static GameStatus getInstance()
	{
		return GameStatus.INSTANCE;
	}

	public Map<Integer, DevelopmentCardInformation> getDevelopmentCardsBuilding()
	{
		return this.developmentCardsBuilding;
	}

	public Map<Integer, DevelopmentCardInformation> getDevelopmentCardsCharacter()
	{
		return this.developmentCardsCharacter;
	}

	public Map<Integer, DevelopmentCardInformation> getDevelopmentCardsTerritory()
	{
		return this.developmentCardsTerritory;
	}

	public Map<Integer, DevelopmentCardInformation> getDevelopmentCardsVenture()
	{
		return this.developmentCardsVenture;
	}

	public Map<CardType, Map<Integer, DevelopmentCardInformation>> getDevelopmentCards()
	{
		return this.developmentCards;
	}

	public Map<Integer, LeaderCardInformation> getLeaderCards()
	{
		return this.leaderCards;
	}

	public Map<Integer, ExcommunicationTileInformation> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public Map<Integer, PersonalBonusTileInformation> getPersonalBonusTiles()
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
