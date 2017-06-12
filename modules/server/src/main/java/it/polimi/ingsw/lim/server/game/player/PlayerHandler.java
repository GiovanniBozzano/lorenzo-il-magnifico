package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionReward;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PlayerHandler
{
	private final PlayerCardHandler playerCardHandler = new PlayerCardHandler(this);
	private final PlayerResourceHandler playerResourceHandler = new PlayerResourceHandler(3, 2, 2);
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions = new EnumMap<>(FamilyMemberType.class);
	private final List<Modifier<? extends Event>> activeModifiers = new ArrayList<>();
	private final List<Modifier<? extends Event>> temporaryModifiers = new ArrayList<>();
	private final PersonalBonusTile personalBonusTile;
	private boolean isOnline = true;
	private ActionReward currentActionReward;
	private int currentProductionValue = 0;

	public PlayerHandler(PersonalBonusTile personalBonusTile)
	{
		this.personalBonusTile = personalBonusTile;
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

	public List<Modifier<? extends Event>> getActiveModifiers()
	{
		return this.activeModifiers;
	}

	public PersonalBonusTile getPersonalBonusTile()
	{
		return this.personalBonusTile;
	}

	public List<Modifier<? extends Event>> getTemporaryModifiers()
	{
		return this.temporaryModifiers;
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
}
