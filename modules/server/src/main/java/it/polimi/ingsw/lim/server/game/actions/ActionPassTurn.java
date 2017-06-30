package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsPassTurn;
import it.polimi.ingsw.lim.server.game.player.Player;

public class ActionPassTurn extends ActionInformationsPassTurn implements IAction
{
	private transient final Player player;

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
			throw new GameActionFailedException("");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != null) {
			throw new GameActionFailedException("");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
