package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsChooseRewardProduction extends ActionInformations
{
	private final int servant;

	public ActionInformationsChooseRewardProduction(int servants)
	{
		super(ActionType.CHOOSE_REWARD_PRODUCTION);
		this.servant = servants;
	}

	public int getServant()
	{
		return this.servant;
	}
}
