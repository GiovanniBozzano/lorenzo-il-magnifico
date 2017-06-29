package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActionChooseRewardCouncilPrivilege extends ActionInformationsChooseRewardCouncilPrivilege implements IAction
{
	private transient final Player player;

	public ActionChooseRewardCouncilPrivilege(List<Integer> councilPalaceRewardIndexes, Player player)
	{
		super(councilPalaceRewardIndexes);
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
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE) {
			return false;
		}
		if (this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE) != this.getCouncilPrivilegeRewardIndexes().size()) {
			return false;
		}
		// check if all rewards are different
		Set<Integer> set = new HashSet<>(this.getCouncilPrivilegeRewardIndexes());
		if ((this.getCouncilPrivilegeRewardIndexes().size() > gameHandler.getBoardHandler().getMatchCouncilPrivilegeRewards().size() && set.size() != gameHandler.getBoardHandler().getMatchCouncilPrivilegeRewards().size()) || set.size() != this.getCouncilPrivilegeRewardIndexes().size()) {
			return false;
		}
		for (int councilPrivilegeReward : this.getCouncilPrivilegeRewardIndexes()) {
			if (!gameHandler.getBoardHandler().getMatchCouncilPrivilegeRewards().keySet().contains(councilPrivilegeReward)) {
				return false;
			}
		}
		return true;
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
		this.player.getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
		List<ResourceAmount> resourceReward = new ArrayList<>();
		for (int councilPalaceRewardIndex : this.getCouncilPrivilegeRewardIndexes()) {
			resourceReward.addAll(gameHandler.getBoardHandler().getMatchCouncilPrivilegeRewards().get(councilPalaceRewardIndex));
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.COUNCIL_PRIVILEGE);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		if (gameHandler.getCurrentPhase() == Phase.LEADER) {
			gameHandler.sendGameUpdate(this.player);
			return;
		}
		gameHandler.nextTurn();
	}
}
