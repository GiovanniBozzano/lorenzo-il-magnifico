package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;

@FunctionalInterface public interface IExpectedActionHandler
{
	void execute(ControllerGame controllerGame, ExpectedAction expectedAction);
}
