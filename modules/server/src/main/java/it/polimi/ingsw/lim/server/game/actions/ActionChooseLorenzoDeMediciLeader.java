package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.cards.CardsHandler;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseLorenzoDeMediciLeader extends ActionInformationsChooseLorenzoDeMediciLeader implements IAction
{
	private transient final Player player;

	public ActionChooseLorenzoDeMediciLeader(int leaderCardIndex, Player player)
	{
		super(leaderCardIndex);
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
		if (gameHandler.getExpectedAction() != null) {
			return false;
		}
		// check if the chosen leader card is valid
		List<Integer> availableLeaderCards = new ArrayList<>();
		for (Player otherPlayer : gameHandler.getTurnOrder()) {
			if (otherPlayer != this.player) {
				for (LeaderCard leaderCard : otherPlayer.getPlayerCardHandler().getLeaderCards()) {
					if (leaderCard.isPlayed()) {
						availableLeaderCards.add(leaderCard.getIndex());
					}
				}
			}
		}
		return availableLeaderCards.contains(this.getLeaderCardIndex());
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
		gameHandler.setCurrentPhase(Phase.LEADER);
		this.player.getPlayerCardHandler().getLeaderCards().remove(this.player.getPlayerCardHandler().getLeaderCardFromIndex(14));
		LeaderCard leaderCard = CardsHandler.getleaderCardFromIndex(this.getLeaderCardIndex());
		if (leaderCard == null) {
			return;
		}
		leaderCard.setPlayed(true);
		this.player.getPlayerCardHandler().getLeaderCards().add(leaderCard);
		if (leaderCard instanceof LeaderCardModifier) {
			this.player.getActiveModifiers().add(((LeaderCardModifier) leaderCard).getModifier());
		} else {
			((LeaderCardReward) leaderCard).setActivated(true);
			EventGainResources eventGainResources = new EventGainResources(this.player, ((LeaderCardReward) leaderCard).getReward().getResourceAmounts(), ResourcesSource.LEADER_CARDS);
			eventGainResources.applyModifiers(this.player.getActiveModifiers());
			this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
			if (((LeaderCardReward) leaderCard).getReward().getActionReward() != null) {
				this.player.setCurrentActionReward(((LeaderCardReward) leaderCard).getReward().getActionReward());
				gameHandler.setExpectedAction(((LeaderCardReward) leaderCard).getReward().getActionReward().getRequestedAction());
				gameHandler.sendGameUpdateExpectedAction(this.player, ((LeaderCardReward) leaderCard).getReward().getActionReward().createExpectedAction(gameHandler, this.player));
				return;
			}
			int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
			if (councilPrivilegesCount > 0) {
				this.player.getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
				this.player.getCouncilPrivileges().add(councilPrivilegesCount);
				gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
				gameHandler.sendGameUpdateExpectedAction(this.player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
				return;
			}
		}
		gameHandler.setExpectedAction(null);
		gameHandler.sendGameUpdate(this.player);
	}
}
