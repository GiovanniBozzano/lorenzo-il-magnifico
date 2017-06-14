package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ExpectedActionChooseRewardHarvest extends ExpectedAction
{
	private final int servant;

	public ExpectedActionChooseRewardHarvest(int servants)
	{
		super(ActionType.CHOOSE_REWARD_HARVEST);
		this.servant = servants;
	}

	public int getServant()
	{
		return this.servant;
	}
}
