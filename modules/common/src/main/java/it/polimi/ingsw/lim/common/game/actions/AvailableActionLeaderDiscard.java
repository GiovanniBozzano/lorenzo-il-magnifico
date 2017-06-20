package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class AvailableActionLeaderDiscard extends AvailableAction
{
	private final int leaderCardIndex;

	public AvailableActionLeaderDiscard(int leaderCardIndex)
	{
		super(ActionType.LEADER_DISCARD);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
