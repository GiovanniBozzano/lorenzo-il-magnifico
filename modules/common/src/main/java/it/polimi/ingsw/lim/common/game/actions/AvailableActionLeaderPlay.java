package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class AvailableActionLeaderPlay extends AvailableAction
{
	private final int leaderCardIndex;

	public AvailableActionLeaderPlay(int leaderCardIndex)
	{
		super(ActionType.LEADER_PLAY);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
