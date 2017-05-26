package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformations;
import it.polimi.ingsw.lim.server.network.Connection;

public abstract class Action extends ActionInformations
{
	private Connection player;

	public Action(ActionType actionType)
	{
		super(actionType);
	}

	public abstract void apply();

	public abstract void isLegal();

	public Connection getPlayer()
	{
		return this.player;
	}
}
