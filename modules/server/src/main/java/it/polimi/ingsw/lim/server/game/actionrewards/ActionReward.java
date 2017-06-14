package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.network.Connection;

public abstract class ActionReward
{
	private final String description;
	private final ActionType requestedAction;

	ActionReward(String description, ActionType requestedAction)
	{
		this.description = description;
		this.requestedAction = requestedAction;
	}

	public abstract ExpectedAction createExpectedAction(GameHandler gameHandler, Connection player);

	public String getDescription()
	{
		return this.description;
	}

	public ActionType getRequestedAction()
	{
		return this.requestedAction;
	}
}
