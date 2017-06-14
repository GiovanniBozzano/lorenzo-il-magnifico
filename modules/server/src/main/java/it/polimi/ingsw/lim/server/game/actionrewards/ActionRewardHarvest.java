package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardHarvest extends ActionReward
{
	private final int value;

	public ActionRewardHarvest(String description, int value)
	{
		super(description, ActionType.CHOOSE_REWARD_HARVEST);
		this.value = value;
	}

	@Override
	public ExpectedAction createExpectedAction(GameHandler gameHandler, Connection player)
	{
		return null;
	}

	public int getValue()
	{
		return this.value;
	}
}