package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActionChooseRewardCouncilPrivilege extends ActionInformationChooseRewardCouncilPrivilege implements IAction
{
	private final transient Player player;

	public ActionChooseRewardCouncilPrivilege(List<Integer> councilPalaceRewardIndexes, Player player)
	{
		super(councilPalaceRewardIndexes);
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
		if (this.player.getRoom().getGameHandler().getExpectedAction() != ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE) {
			throw new GameActionFailedException("This action was not expected");
		}
		if (this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE) != this.getCouncilPrivilegeRewardIndexes().size()) {
			throw new GameActionFailedException("You did not send the right rewards amount");
		}
		// check if all rewards are different
		Set<Integer> set = new HashSet<>(this.getCouncilPrivilegeRewardIndexes());
		if ((this.getCouncilPrivilegeRewardIndexes().size() > this.player.getRoom().getGameHandler().getBoardHandler().getMatchCouncilPrivilegeRewards().size() && set.size() != this.player.getRoom().getGameHandler().getBoardHandler().getMatchCouncilPrivilegeRewards().size()) || set.size() != this.getCouncilPrivilegeRewardIndexes().size()) {
			throw new GameActionFailedException("All the rewards have to be different");
		}
		for (int councilPrivilegeReward : this.getCouncilPrivilegeRewardIndexes()) {
			if (!this.player.getRoom().getGameHandler().getBoardHandler().getMatchCouncilPrivilegeRewards().keySet().contains(councilPrivilegeReward)) {
				throw new GameActionFailedException("The rewards you chose are not valid");
			}
		}
	}

	@Override
	public void apply()
	{
		List<ResourceAmount> resourceReward = new ArrayList<>();
		for (int councilPalaceRewardIndex : this.getCouncilPrivilegeRewardIndexes()) {
			resourceReward.addAll(this.player.getRoom().getGameHandler().getBoardHandler().getMatchCouncilPrivilegeRewards().get(councilPalaceRewardIndex));
		}
		this.player.getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
		this.player.getPlayerResourceHandler().getResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.COUNCIL_PRIVILEGE);
		eventGainResources.fire();
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " chose council privileges rewards");
		if (Utils.checkLeaderPhase(this.player)) {
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
