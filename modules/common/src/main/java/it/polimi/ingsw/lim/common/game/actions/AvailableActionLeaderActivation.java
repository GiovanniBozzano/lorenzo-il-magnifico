package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class AvailableActionLeaderActivation extends AvailableAction
{
	private final int leaderCardIndex;

	public AvailableActionLeaderActivation(int leaderCardIndex)
	{
		super(ActionType.LEADER_ACTIVATION);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
