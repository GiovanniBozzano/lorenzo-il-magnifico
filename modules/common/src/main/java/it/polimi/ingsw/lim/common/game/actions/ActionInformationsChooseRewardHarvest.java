package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsChooseRewardHarvest extends ActionInformations
{
	private final int servants;

	public ActionInformationsChooseRewardHarvest(int servants)
	{
		super(ActionType.CHOOSE_REWARD_HARVEST);
		this.servants = servants;
	}

	public int getServants()
	{
		return this.servants;
	}
}
