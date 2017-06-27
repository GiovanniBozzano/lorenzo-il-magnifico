package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsLeaderPlay extends ActionInformations
{
	private final int leaderCardIndex;

	public ActionInformationsLeaderPlay(int leaderCardIndex)
	{
		super(ActionType.LEADER_PLAY);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
