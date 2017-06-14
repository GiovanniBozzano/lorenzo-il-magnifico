package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ExpectedActionChooseRewardProduction extends ExpectedAction
{
	private final int servant;

	public ExpectedActionChooseRewardProduction(int servants)
	{
		super(ActionType.CHOOSE_REWARD_PRODUCTION);
		this.servant = servants;
	}

	public int getServant()
	{
		return this.servant;
	}
}
