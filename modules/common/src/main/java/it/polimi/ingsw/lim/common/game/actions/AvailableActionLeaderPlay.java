package it.polimi.ingsw.lim.common.game.actions;

public class AvailableActionLeaderPlay extends AvailableAction
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
