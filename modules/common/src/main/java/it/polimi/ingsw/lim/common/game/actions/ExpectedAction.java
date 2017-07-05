package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.io.Serializable;

/**
 * <p>This class represents a game forced action as a network object and is
 * available to both client and server to communicate.
 *
 * <p>The player is forced to perform the action represented by this object.
 */
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
