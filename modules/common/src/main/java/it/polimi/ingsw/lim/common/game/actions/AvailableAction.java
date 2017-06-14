package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class AvailableAction
{
	private final ActionType actionType;

	public AvailableAction(ActionType actionType)
	{
		this.actionType = actionType;
	}

	public ActionType getActionType()
	{
		return this.actionType;
	}
}
