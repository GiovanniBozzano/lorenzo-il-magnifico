package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsProductionTrade extends ActionInformations
{
	private final int developmentCardBuildingIndex;

	public ActionInformationsProductionTrade(int developmentCardBuildingIndex)
	{
		super(ActionType.PRODUCTION_TRADE);
		this.developmentCardBuildingIndex = developmentCardBuildingIndex;
	}

	public int getDevelopmentCardBuildingIndex()
	{
		return this.developmentCardBuildingIndex;
	}
}
