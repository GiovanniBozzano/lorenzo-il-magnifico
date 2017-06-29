package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;

public interface IAction
{
	void isLegal() throws GameActionFailedException;

	void apply() throws GameActionFailedException;
}
