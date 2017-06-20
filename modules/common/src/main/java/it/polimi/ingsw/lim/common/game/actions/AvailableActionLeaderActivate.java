package it.polimi.ingsw.lim.common.game.actions;

public class AvailableActionLeaderActivate extends AvailableAction
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
