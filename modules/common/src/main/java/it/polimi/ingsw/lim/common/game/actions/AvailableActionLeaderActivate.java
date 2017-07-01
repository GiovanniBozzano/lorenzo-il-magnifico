package it.polimi.ingsw.lim.common.game.actions;

import java.io.Serializable;

public class AvailableActionLeaderActivate implements Serializable
{
	private final int leaderCardIndex;

	public AvailableActionLeaderActivate(int leaderCardIndex)
	{
		super();
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
