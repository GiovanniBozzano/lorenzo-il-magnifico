package it.polimi.ingsw.lim.common.game.actions;

import java.io.Serializable;

public class AvailableActionLeaderDiscard implements Serializable
{
	private final int leaderCardIndex;

	public AvailableActionLeaderDiscard(int leaderCardIndex)
	{
		super();
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
