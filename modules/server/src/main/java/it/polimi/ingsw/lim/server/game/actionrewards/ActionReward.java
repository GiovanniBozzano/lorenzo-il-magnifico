package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.server.game.actions.IAction;
import it.polimi.ingsw.lim.server.game.player.Player;

public abstract class ActionReward
{
	private final String description;
	private final ActionType requestedAction;

	/**
	 * <p>This class represents an action as a reward object.
	 *
	 * @param description the textual description of the action.
	 * @param requestedAction the {@link ActionType} of the requested {@link
	 * IAction}.
	 */
	ActionReward(String description, ActionType requestedAction)
	{
		this.description = description;
		this.requestedAction = requestedAction;
	}

	public abstract ExpectedAction createExpectedAction(Player player);

	/**
	 * <p>Builds the description of this {@link ActionReward}.
	 *
	 * @return the {@link String} representing the description.
	 */
	public String getDescription()
	{
		return this.description;
	}

	/**
	 * <p>Returns the {@link ActionType} of the requested {@link IAction}.
	 *
	 * @return the requested {@link ActionType}.
	 */
	public ActionType getRequestedAction()
	{
		return this.requestedAction;
	}
}
