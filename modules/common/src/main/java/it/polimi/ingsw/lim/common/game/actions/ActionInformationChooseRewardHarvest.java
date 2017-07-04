package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationChooseRewardHarvest extends ActionInformation
{
	private final int servants;

	public ActionInformationChooseRewardHarvest(int servants)
	{
		super(ActionType.CHOOSE_REWARD_HARVEST);
		this.servants = servants;
	}

	public int getServants()
	{
		return this.servants;
	}
}
