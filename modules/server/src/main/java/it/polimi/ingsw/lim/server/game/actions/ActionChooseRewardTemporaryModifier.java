package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionChooseRewardTemporaryModifier implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;

	public ActionChooseRewardTemporaryModifier(Connection player, FamilyMemberType familyMemberType)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
	}

	@Override
	public boolean isLegal()
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player);
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
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER) {
			return false;
		}
		return true;
	}

	@Override
	public void apply()
	{
	}
}
