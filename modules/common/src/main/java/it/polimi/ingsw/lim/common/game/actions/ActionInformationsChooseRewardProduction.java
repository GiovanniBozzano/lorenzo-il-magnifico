package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsChooseRewardProduction extends ActionInformations
{
	private final int servants;

	public ActionInformationsChooseRewardProduction(int servants)
	{
		super(ActionType.CHOOSE_REWARD_PRODUCTION);
		this.servants = servants;
	}

	public int getServants()
	{
		return this.servants;
	}
}
