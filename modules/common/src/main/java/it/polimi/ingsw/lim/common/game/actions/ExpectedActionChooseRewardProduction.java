package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ExpectedActionChooseRewardProduction extends ExpectedAction
{
	private final int value;

	public ExpectedActionChooseRewardProduction(int value)
	{
		super(ActionType.CHOOSE_REWARD_PRODUCTION);
		this.value = value;
	}

	public int getValue()
	{
		return this.value;
	}
}
