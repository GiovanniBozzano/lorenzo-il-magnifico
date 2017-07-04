package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.game.actions.ActionInformation;
import it.polimi.ingsw.lim.server.game.player.Player;

@FunctionalInterface public interface IActionTransformer
{
	IAction transform(ActionInformation actionInformation, Player player);
}
