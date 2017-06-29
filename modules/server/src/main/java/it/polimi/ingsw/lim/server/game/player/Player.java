package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionReward;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardVenture;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.*;

public class Player
{
	private static final Map<Integer, Integer> TERRITORY_SLOTS_CONDITIONS = new HashMap<>();

	static {
		Player.TERRITORY_SLOTS_CONDITIONS.put(0, 0);
		Player.TERRITORY_SLOTS_CONDITIONS.put(1, 0);
		Player.TERRITORY_SLOTS_CONDITIONS.put(2, 3);
		Player.TERRITORY_SLOTS_CONDITIONS.put(3, 7);
		Player.TERRITORY_SLOTS_CONDITIONS.put(4, 12);
		Player.TERRITORY_SLOTS_CONDITIONS.put(5, 18);
	}

	private static final Map<Period, Integer> EXCOMMUNICATION_CONDITIONS = new EnumMap<>(Period.class);

	static {
		Player.EXCOMMUNICATION_CONDITIONS.put(Period.FIRST, 3);
		Player.EXCOMMUNICATION_CONDITIONS.put(Period.SECOND, 4);
		Player.EXCOMMUNICATION_CONDITIONS.put(Period.THIRD, 5);
	}

	private Connection connection;
	private final int index;
	private final PlayerCardHandler playerCardHandler = new PlayerCardHandler();
	private final PlayerResourceHandler playerResourceHandler = new PlayerResourceHandler(3, 2, 2);
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions = new EnumMap<>(FamilyMemberType.class);
	private final List<Modifier> activeModifiers = new ArrayList<>();
	private final List<Modifier> temporaryModifiers = new ArrayList<>();
	private PersonalBonusTile personalBonusTile;
	private int availableTurns = 4;
	private boolean isOnline = true;
	private ActionReward currentActionReward;
	private int currentProductionValue = 0;
	private final List<Integer> availableLeaderCards = new ArrayList<>();

	public Player(Connection connection, int index)
	{
		this.connection = connection;
		this.index = index;
		this.familyMembersPositions.put(FamilyMemberType.BLACK, BoardPosition.NONE);
		this.familyMembersPositions.put(FamilyMemberType.ORANGE, BoardPosition.NONE);
		this.familyMembersPositions.put(FamilyMemberType.WHITE, BoardPosition.NONE);
		this.familyMembersPositions.put(FamilyMemberType.NEUTRAL, BoardPosition.NONE);
	}

	public boolean isOccupyingBoardPosition(BoardPosition boardPosition)
	{
		for (BoardPosition playerBoardPosition : this.familyMembersPositions.values()) {
			if (playerBoardPosition == boardPosition) {
				return true;
			}
		}
		return false;
	}

	public boolean isTerritorySlotAvailable(int territorySlot)
	{
		return Player.TERRITORY_SLOTS_CONDITIONS.containsKey(territorySlot) && this.playerResourceHandler.getResources().get(ResourceType.MILITARY_POINT) >= Player.TERRITORY_SLOTS_CONDITIONS.get(territorySlot);
	}

	public boolean isExcommunicated(Period period)
	{
		return this.playerResourceHandler.getResources().get(ResourceType.FAITH_POINT) < Player.EXCOMMUNICATION_CONDITIONS.get(period);
	}

	public void convertToVictoryPoints(boolean countingCharacters, boolean countingTerritories, boolean countingVentures)
	{
		this.playerResourceHandler.addResource(ResourceType.VICTORY_POINT, (this.playerResourceHandler.getResources().get(ResourceType.COIN) + this.playerResourceHandler.getResources().get(ResourceType.WOOD) + this.playerResourceHandler.getResources().get(ResourceType.STONE) + this.playerResourceHandler.getResources().get(ResourceType.SERVANT)) / 5);
		if (countingCharacters) {
			this.playerResourceHandler.addResource(ResourceType.VICTORY_POINT, PlayerCardHandler.getDevelopmentCardsCharacterPrices().get(this.playerCardHandler.getDevelopmentCardsNumber(CardType.CHARACTER)));
		}
		if (countingTerritories) {
			this.playerResourceHandler.addResource(ResourceType.VICTORY_POINT, PlayerCardHandler.getDevelopmentCardsTerritoryPrices().get(this.playerCardHandler.getDevelopmentCardsNumber(CardType.TERRITORY)));
		}
		if (countingVentures) {
			for (DevelopmentCardVenture developmentCardVenture : this.playerCardHandler.getDevelopmentCards(CardType.VENTURE, DevelopmentCardVenture.class)) {
				this.playerResourceHandler.addResource(ResourceType.VICTORY_POINT, developmentCardVenture.getVictoryValue());
			}
		}
	}

	public Connection getConnection()
	{
		return this.connection;
	}

	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	public int getIndex()
	{
		return this.index;
	}

	public PlayerCardHandler getPlayerCardHandler()
	{
		return this.playerCardHandler;
	}

	public PlayerResourceHandler getPlayerResourceHandler()
	{
		return this.playerResourceHandler;
	}

	public Map<FamilyMemberType, BoardPosition> getFamilyMembersPositions()
	{
		return this.familyMembersPositions;
	}

	public List<Modifier> getActiveModifiers()
	{
		return this.activeModifiers;
	}

	public List<Modifier> getTemporaryModifiers()
	{
		return this.temporaryModifiers;
	}

	public PersonalBonusTile getPersonalBonusTile()
	{
		return this.personalBonusTile;
	}

	public void setPersonalBonusTile(PersonalBonusTile personalBonusTile)
	{
		this.personalBonusTile = personalBonusTile;
	}

	public int getAvailableTurns()
	{
		return this.availableTurns;
	}

	public void decreaseAvailableTurns()
	{
		this.availableTurns--;
	}

	public void resetAvailableTurns()
	{
		this.availableTurns = 4;
	}

	public boolean isOnline()
	{
		return this.isOnline;
	}

	public void setOnline(boolean online)
	{
		this.isOnline = online;
	}

	public ActionReward getCurrentActionReward()
	{
		return this.currentActionReward;
	}

	public void setCurrentActionReward(ActionReward actionReward)
	{
		this.currentActionReward = actionReward;
	}

	public int getCurrentProductionValue()
	{
		return this.currentProductionValue;
	}

	public void setCurrentProductionValue(int currentProductionValue)
	{
		this.currentProductionValue = currentProductionValue;
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
