package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.server.network.Connection;

public class ActionLeaderDiscard implements IAction
{
	private final Connection player;
	private final int cardLeaderIndex;

	public ActionLeaderDiscard(Connection player, int cardLeaderIndex)
	{
		this.player = player;
		this.cardLeaderIndex = cardLeaderIndex;
	}

	@Override
	public boolean isLegal()
	{
		return false;
	}

	@Override
	public void apply()
	{
	}

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}

	public int getCardLeaderIndex()
	{
		return this.cardLeaderIndex;
	}
}
