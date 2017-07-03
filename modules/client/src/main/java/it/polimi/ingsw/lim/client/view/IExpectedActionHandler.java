package it.polimi.ingsw.lim.client.view;

import it.polimi.ingsw.lim.client.view.gui.ControllerGame;

@FunctionalInterface public interface IExpectedActionHandler
{
	void execute(ControllerGame controllerGame);
}
