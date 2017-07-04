package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationLeaderPlay extends ActionInformation
{
	private final int leaderCardIndex;

	public ActionInformationLeaderPlay(int leaderCardIndex)
	{
		super(ActionType.LEADER_PLAY);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
