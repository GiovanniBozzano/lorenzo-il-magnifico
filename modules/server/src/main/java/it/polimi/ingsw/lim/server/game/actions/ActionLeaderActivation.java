package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderActivation;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionLeaderActivation extends ActionInformationsLeaderActivation implements IAction
{
	private final Connection player;
	private LeaderCard leaderCard;

	public ActionLeaderActivation(int leaderCardIndex, Connection player)
	{
		super(leaderCardIndex);
		this.player = player;
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
		if (gameHandler.getExpectedAction() != null) {
			return false;
		}
		// check if the player has the leader card
		boolean owned = false;
		for (LeaderCard currentLeaderCard : this.player.getPlayerHandler().getPlayerCardHandler().getLeaderCards()) {
			if (this.getLeaderCardIndex() == currentLeaderCard.getIndex() && currentLeaderCard instanceof LeaderCardReward) {
				this.leaderCard = currentLeaderCard;
				owned = true;
				break;
			}
		}
		if (!owned) {
			return false;
		}
		// check if the leader card has been played
		if (!this.leaderCard.isPlayed()) {
			return false;
		}
		// check if the leader card hasn't been already activated
		return !((LeaderCardReward) this.leaderCard).isActivated();
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
		gameHandler.setCurrentPhase(Phase.LEADER);
		((LeaderCardReward) this.leaderCard).setActivated(true);
		EventGainResources eventGainResources = new EventGainResources(this.player, ((LeaderCardReward) this.leaderCard).getReward().getResourceAmounts(), ResourcesSource.LEADER_CARDS);
		eventGainResources.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		this.player.getPlayerHandler().getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		if (((LeaderCardReward) this.leaderCard).getReward().getActionReward() != null) {
			gameHandler.sendGameUpdateExpectedAction(this.player, ((LeaderCardReward) this.leaderCard).getReward().getActionReward().createExpectedAction(gameHandler, this.player));
			return;
		}
		int councilPrivilegesCount = this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
			this.player.getPlayerHandler().getCouncilPrivileges().add(councilPrivilegesCount);
			gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
			gameHandler.sendGameUpdateExpectedAction(this.player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
			return;
		}
		gameHandler.sendGameUpdate(this.player);
	}
}
