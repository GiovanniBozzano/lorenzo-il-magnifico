package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardProduction;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardProduction extends ActionReward
{
	private final int value;

	public ActionRewardProduction(String description, int value)
	{
		super(description, ActionType.CHOOSE_REWARD_PRODUCTION);
		this.value = value;
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
}