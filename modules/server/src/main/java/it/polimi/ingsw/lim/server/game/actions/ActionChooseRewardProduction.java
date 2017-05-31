package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.server.network.Connection;

public class ActionChooseRewardProduction implements IAction
{
	private final Connection player;
	private final int servant;

	public ActionChooseRewardProduction(Connection player, int servants)
	{
		this.player = player;
		this.servant = servants;
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

	public int getServant()
	{
		return this.servant;
	}
}
