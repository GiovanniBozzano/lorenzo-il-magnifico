package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardProduction;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardProduction extends ActionReward
{
	private final int value;
	private final boolean applyModifiers;

	public ActionRewardProduction(String description, int value, boolean applyModifiers)
	{
		super(description, ActionType.CHOOSE_REWARD_PRODUCTION);
		this.value = value;
		this.applyModifiers = applyModifiers;
	}

	@Override
	public ExpectedAction createExpectedAction(GameHandler gameHandler, Connection player)
	{
		return new ExpectedActionChooseRewardProduction(this.value);
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