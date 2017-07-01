package it.polimi.ingsw.lim.common.game.actions;

import java.io.Serializable;

public class AvailableActionLeaderPlay implements Serializable
{
	private final int leaderCardIndex;

	public AvailableActionLeaderPlay(int leaderCardIndex)
	{
		super();
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
