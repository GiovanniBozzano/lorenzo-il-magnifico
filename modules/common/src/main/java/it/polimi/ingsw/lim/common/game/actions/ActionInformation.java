package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.io.Serializable;

public class ActionInformation implements Serializable
{
	private final ActionType actionType;

	ActionInformation(ActionType actionType)
	{
		this.actionType = actionType;
	}

	public ActionType getActionType()
	{
		return this.actionType;
	}
}
