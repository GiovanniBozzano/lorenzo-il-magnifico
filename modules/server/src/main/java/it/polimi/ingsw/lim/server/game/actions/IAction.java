package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.server.network.Connection;

public interface IAction
{
	boolean isLegal();

	void apply();

	Connection getPlayer();
}
