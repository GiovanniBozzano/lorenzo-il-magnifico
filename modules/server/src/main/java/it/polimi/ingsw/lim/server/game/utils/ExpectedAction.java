package it.polimi.ingsw.lim.server.game.utils;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ExpectedAction
{
	private ActionType actionType;
	private Integer actionTypeValue;

	public ExpectedAction(ActionType actionType, Integer actionTypeValue)
	{
		this.actionType = actionType;
		this.actionTypeValue = actionTypeValue;
	}

	public ActionType getActionType()
	{
		return this.actionType;
	}

	public Integer getActionTypeValue()
	{
		return this.actionTypeValue;
	}
}
