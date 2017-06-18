package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsLeaderDiscard extends ActionInformations
{
	private final int leaderCardIndex;

	public ActionInformationsLeaderDiscard(int leaderCardIndex)
	{
		super(ActionType.LEADER_DISCARD);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
