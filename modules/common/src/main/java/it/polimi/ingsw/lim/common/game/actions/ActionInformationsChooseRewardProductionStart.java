package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsChooseRewardProductionStart extends ActionInformations
{
	private final int servants;

	public ActionInformationsChooseRewardProductionStart(int servants)
	{
		super(ActionType.CHOOSE_REWARD_PRODUCTION_START);
		this.servants = servants;
	}

	public int getServants()
	{
		return this.servants;
	}
}
