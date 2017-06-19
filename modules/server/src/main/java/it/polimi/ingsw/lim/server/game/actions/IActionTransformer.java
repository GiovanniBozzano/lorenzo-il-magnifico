package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.game.actions.ActionInformations;
import it.polimi.ingsw.lim.server.game.player.Player;

@FunctionalInterface public interface IActionTransformer
{
	IAction transform(ActionInformations actionInformations, Player player);
}
