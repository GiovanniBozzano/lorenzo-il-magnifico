package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.utils.CouncilPalaceReward;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActionChooseRewardCouncilPrivilege implements IAction
{
	private final Connection player;
	private final List<List<Integer>> councilPalaceRewardIndexes;

	public ActionChooseRewardCouncilPrivilege(Connection player, List<List<Integer>> councilPalaceRewardIndexes)
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
		for (List<Integer> differentIndexes : this.councilPalaceRewardIndexes) {
			boolean validIndexes = false;
			for (Integer councilPrivilegesCount : this.player.getPlayerHandler().getCouncilPrivileges()) {
				if (councilPrivilegesCount == differentIndexes.size()) {
					validIndexes = true;
					break;
				}
			}
			if (!validIndexes) {
				return false;
			}
		}
		for (List<Integer> differentIndexes : this.councilPalaceRewardIndexes) {
			// check if all rewards are different
			Set<Integer> set = new HashSet<>(differentIndexes);
			if ((differentIndexes.size() > BoardHandler.getCouncilPrivilegeRewards().size() && set.size() != BoardHandler.getCouncilPrivilegeRewards().size()) || set.size() != differentIndexes.size()) {
				return false;
			}
			for (int councilPalaceRewardIndex : differentIndexes) {
				boolean validIndex = false;
				for (CouncilPalaceReward councilPalaceReward : BoardHandler.getCouncilPrivilegeRewards()) {
					if (councilPalaceReward.getIndex() == councilPalaceRewardIndex) {
						validIndex = true;
					}
					if (validIndex) {
						break;
					}
				}
				if (!validIndex) {
					return false;
				}
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
		this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().remove(ResourceType.COUNCIL_PRIVILEGE);
		List<ResourceAmount> resourceReward = new ArrayList<>();
		for (List<Integer> differentIndexes : councilPalaceRewardIndexes) {
			for (int councilPalaceRewardIndex : differentIndexes) {
				resourceReward.addAll(BoardHandler.getCouncilPrivilegeRewards().get(councilPalaceRewardIndex).getResourceAmounts());
			}
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.COUNCIL_PRIVILEGE);
		eventGainResources.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		this.player.getPlayerHandler().getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		if (gameHandler.getPhase() == Phase.LEADER) {
			// TODO aggiorno tutti
			// TODO prosegui turno
		} else {
			gameHandler.nextTurn();
		}
	}
}
