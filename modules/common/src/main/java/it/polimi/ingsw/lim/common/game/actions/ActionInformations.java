package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.io.Serializable;

public class ActionInformations implements Serializable
{
	private final ActionType actionType;

	public ActionInformations(ActionType actionType)
	{
		this.actionType = actionType;
	}

	public ActionType getActionType()
	{
		return this.actionType;
	}
}
