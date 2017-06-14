package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.network.Connection;

public abstract class ActionReward
{
	private final ActionType requestedAction;

	ActionReward(ActionType requestedAction)
	{
		this.requestedAction = requestedAction;
	}

	public abstract ExpectedAction createExpectedAction(GameHandler gameHandler, Connection player);

	public ActionType getRequestedAction()
	{
		return this.requestedAction;
	}
}
