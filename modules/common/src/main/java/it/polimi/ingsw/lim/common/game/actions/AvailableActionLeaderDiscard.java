package it.polimi.ingsw.lim.common.game.actions;

public class AvailableActionLeaderDiscard extends AvailableAction
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
