package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ExpectedActionProductionTrade extends ExpectedAction
{
	private final int developmentCardBuildingIndex;

	public ExpectedActionProductionTrade(int developmentCardBuildingIndex)
	{
		super(ActionType.PRODUCTION_TRADE);
		this.developmentCardBuildingIndex = developmentCardBuildingIndex;
	}

	public int getDevelopmentCardBuildingIndex()
	{
		return this.developmentCardBuildingIndex;
	}
}
