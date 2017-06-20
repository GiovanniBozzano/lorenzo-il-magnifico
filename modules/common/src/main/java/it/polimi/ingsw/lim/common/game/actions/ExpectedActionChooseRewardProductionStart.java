package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ExpectedActionChooseRewardProductionStart extends ExpectedAction
{
	private final int value;

	public ExpectedActionChooseRewardProductionStart(int value)
	{
		super(ActionType.CHOOSE_REWARD_PRODUCTION_START);
		this.value = value;
	}

	public int getValue()
	{
		return this.value;
	}
}
