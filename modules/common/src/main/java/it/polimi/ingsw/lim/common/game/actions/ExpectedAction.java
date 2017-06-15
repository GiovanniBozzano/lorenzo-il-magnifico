package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.io.Serializable;

public class ExpectedAction implements Serializable
{
	private final ActionType actionType;

	public ExpectedAction(ActionType actionType)
	{
		this.actionType = actionType;
	}

	public ActionType getActionType()
	{
		return this.actionType;
	}
}
