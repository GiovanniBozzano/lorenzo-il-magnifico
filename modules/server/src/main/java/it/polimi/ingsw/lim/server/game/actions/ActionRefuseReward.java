package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsRefuseReward;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

public class ActionRefuseReward extends ActionInformationsRefuseReward implements IAction
{
	private final Player player;

	public ActionRefuseReward(Player player)
	{
		super();
		this.player = player;
	}

	@Override
	public boolean isLegal()
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			return false;
		}
		// check if the game has started
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return false;
		}
		// check if it is the player's turn
		if (this.player != gameHandler.getTurnPlayer()) {
			return false;
		}
		// check whether the server expects the player to make this action
		return gameHandler.getExpectedAction() == ActionType.CHOOSE_REWARD_GET_DEVELOPMENT_CARD;
	}

	@Override
	public void apply()
	{
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		if (gameHandler.getCurrentPhase() == Phase.LEADER) {
			gameHandler.setExpectedAction(null);
			gameHandler.sendGameUpdate(this.player);
			return;
		}
		gameHandler.nextTurn();
	}
}
