package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardHarvest;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardHarvest extends ActionReward
{
	private final int value;
	private final boolean applyModifiers;

	public ActionRewardHarvest(String description, int value, boolean applyModifiers)
	{
		super(description, ActionType.CHOOSE_REWARD_HARVEST);
		this.value = value;
		this.applyModifiers = applyModifiers;
	}

	@Override
	public ExpectedAction createExpectedAction(GameHandler gameHandler, Connection player)
	{
		return new ExpectedActionChooseRewardHarvest(this.value);
	}

	public int getValue()
	{
		return this.value;
	}

	public boolean isApplyModifiers()
	{
		return this.applyModifiers;
	}
}