package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsPassTurn;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionPassTurn extends ActionInformationsPassTurn implements IAction
{
	private final transient Player player;

	public ActionPassTurn(Player player)
	{
		super();
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if it is the player's turn
		if (this.player != this.player.getRoom().getGameHandler().getTurnPlayer()) {
			throw new GameActionFailedException("It's not your turn");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != null) {
			throw new GameActionFailedException("This action was not expected");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " passed his turn");
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
