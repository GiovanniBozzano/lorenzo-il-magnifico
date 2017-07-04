package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationLeaderDiscard extends ActionInformation
{
	private final int leaderCardIndex;

	public ActionInformationLeaderDiscard(int leaderCardIndex)
	{
		super(ActionType.LEADER_DISCARD);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
