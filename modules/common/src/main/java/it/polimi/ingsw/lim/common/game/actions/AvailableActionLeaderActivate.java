package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class AvailableActionLeaderActivate extends AvailableAction
{
	private final int leaderCardIndex;

	public AvailableActionLeaderActivate(int leaderCardIndex)
	{
		super(ActionType.LEADER_ACTIVATE);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
