package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionReward;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Player
{
	private Connection connection;
	private final int index;
	private final PlayerCardHandler playerCardHandler = new PlayerCardHandler();
	private final PlayerResourceHandler playerResourceHandler = new PlayerResourceHandler(3, 2, 2);
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions = new EnumMap<>(FamilyMemberType.class);
	private final List<Modifier> activeModifiers = new ArrayList<>();
	private final List<Modifier> temporaryModifiers = new ArrayList<>();
	private final List<Integer> councilPrivileges = new ArrayList<>();
	private PersonalBonusTile personalBonusTile;
	private int availableTurns = 4;
	private boolean isOnline = true;
	private ActionReward currentActionReward;
	private int currentProductionValue = 0;
	private List<Integer> availableLeaderCards = new ArrayList<>();

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

	public List<Integer> getCouncilPrivileges()
	{
		return this.councilPrivileges;
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
