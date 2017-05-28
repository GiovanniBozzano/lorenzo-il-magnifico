package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.server.network.Connection;

public class ActionProductionTrade implements IAction
{
	private final Connection player;
	private final int developmentCardBuildingIndex;

	public ActionProductionTrade(Connection player, int developmentCardBuildingIndex)
	{
		this.player = player;
		this.developmentCardBuildingIndex = developmentCardBuildingIndex;
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

	public int getDevelopmentCardBuildingIndex()
	{
		return this.developmentCardBuildingIndex;
	}
}