package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.io.Serializable;

/**
 * <p>This class represents a game action as a network object and is available
 * to both client and server to communicate.
 */
public class ActionInformation implements Serializable
{
	private final ActionType actionType;

	ActionInformation(ActionType actionType)
	{
		this.actionType = actionType;
	}

	/**
	 * <p>Gets the {@link ActionType} of this {@link ActionInformation}.
	 *
	 * @return the {@link ActionType} of this {@link ActionInformation}.
	 */
	public ActionType getActionType()
	{
		return this.actionType;
	}
}
