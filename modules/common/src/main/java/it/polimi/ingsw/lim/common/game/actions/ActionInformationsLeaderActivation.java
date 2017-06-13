package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsLeaderActivation extends ActionInformations
{
	private final int leaderCardIndex;

	public ActionInformationsLeaderActivation(int leaderCardIndex)
	{
		super(ActionType.LEADER_ACTIVATION);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
