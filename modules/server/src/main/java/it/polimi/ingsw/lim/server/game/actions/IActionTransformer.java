package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.game.actions.ActionInformations;
import it.polimi.ingsw.lim.server.network.Connection;

@FunctionalInterface public interface IActionTransformer
{
	IAction transform(ActionInformations actionInformations, Connection player);
}
