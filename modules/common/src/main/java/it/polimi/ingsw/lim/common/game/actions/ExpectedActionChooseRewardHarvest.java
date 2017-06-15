package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ExpectedActionChooseRewardHarvest extends ExpectedAction
{
	private final int value;

	public ExpectedActionChooseRewardHarvest(int value)
	{
		super(ActionType.CHOOSE_REWARD_HARVEST);
		this.value = value;
	}

	public int getValue()
	{
		return this.value;
	}
}
