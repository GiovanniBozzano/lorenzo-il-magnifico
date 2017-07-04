package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationRefuseReward;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

public class ActionRefuseReward extends ActionInformationRefuseReward implements IAction
{
	private final transient Player player;

	public ActionRefuseReward(Player player)
	{
		super();
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if it is the player's turn
		if (this.player != this.player.getRoom().getGameHandler().getTurnPlayer()) {
			throw new GameActionFailedException("It is not your turn");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD) {
			throw new GameActionFailedException("This action was not expected");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " refused to pick a card");
		if (Utils.checkLeaderPhase(this.player)) {
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
