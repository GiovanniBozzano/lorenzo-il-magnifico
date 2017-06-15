package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardTemporaryModifier extends ActionReward
{
	public ActionRewardTemporaryModifier(String description)
	{
		super(description, ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER);
	}

	@Override
	public ExpectedAction createExpectedAction(GameHandler gameHandler, Connection player)
	{
		return new ExpectedAction(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER);
	}
}
