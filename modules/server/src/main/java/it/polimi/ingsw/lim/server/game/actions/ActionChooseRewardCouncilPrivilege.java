package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.utils.CouncilPalaceReward;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActionChooseRewardCouncilPrivilege implements IAction
{
	private final Connection player;
	private final List<Integer> councilPalaceRewardIndexes;

	public ActionChooseRewardCouncilPrivilege(Connection player, List<Integer> councilPalaceRewardIndexes)
	{
		this.player = player;
		this.councilPalaceRewardIndexes = new ArrayList<>(councilPalaceRewardIndexes);
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
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE) {
			return false;
		}
		if (this.player.getPlayerInformations().getPlayerResourceHandler().getTemporaryResources(ResourceType.COUNCIL_PRIVILEGE) != this.councilPalaceRewardIndexes.size()) {
			return false;
		}
		// check if all rewards are different
		Set<Integer> set = new HashSet<>(this.councilPalaceRewardIndexes);
		if ((this.councilPalaceRewardIndexes.size() > BoardHandler.COUNCIL_PRIVILEGE_REWARDS.size() && set.size() != BoardHandler.COUNCIL_PRIVILEGE_REWARDS.size()) || set.size() != this.councilPalaceRewardIndexes.size()) {
			return false;
		}
		for (int councilPalaceRewardIndex : this.councilPalaceRewardIndexes) {
			boolean validIndex = false;
			for (CouncilPalaceReward councilPalaceReward : BoardHandler.COUNCIL_PRIVILEGE_REWARDS) {
				if (validIndex) {
					break;
				}
				if (councilPalaceReward.getIndex() != councilPalaceRewardIndex) {
					continue;
				}
				validIndex = true;
			}
			if (!validIndex) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void apply()
	{
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		this.player.getPlayerInformations().getPlayerResourceHandler().subtractTemporaryResource(new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, this.councilPalaceRewardIndexes.size()));
		List<ResourceAmount> resourceReward = new ArrayList<>();
		for (int councilPalaceRewardIndex : this.councilPalaceRewardIndexes) {
			resourceReward.addAll(BoardHandler.COUNCIL_PRIVILEGE_REWARDS.get(councilPalaceRewardIndex).getResourceAmounts());
		}
		this.player.getPlayerInformations().getPlayerResourceHandler().addTemporaryResources(resourceReward);
		gameHandler.setExpectedAction(null);
		//TODO aggiorno tutti
		//TODO turno del prossimo giocatore
	}
}
