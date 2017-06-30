package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
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
		// check if the chosen leader card is valid
		List<Integer> availableLeaderCards = new ArrayList<>();
		for (Player otherPlayer : this.player.getRoom().getGameHandler().getTurnOrder()) {
			if (otherPlayer != this.player) {
				for (LeaderCard leaderCard : otherPlayer.getPlayerCardHandler().getLeaderCards()) {
					if (leaderCard.isPlayed()) {
						availableLeaderCards.add(leaderCard.getIndex());
					}
				}
			}
		}
		if (!availableLeaderCards.contains(this.getLeaderCardIndex())) {
			throw new GameActionFailedException("");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.LEADER);
		this.player.getPlayerCardHandler().getLeaderCards().remove(this.player.getPlayerCardHandler().getLeaderCardFromIndex(14));
		LeaderCard leaderCard = CardsHandler.getleaderCardFromIndex(this.getLeaderCardIndex());
		if (leaderCard == null) {
			throw new GameActionFailedException("");
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
			if (((LeaderCardReward) leaderCard).getReward().getActionReward() != null && ((LeaderCardReward) leaderCard).getReward().getActionReward().getRequestedAction() != null) {
				this.player.setCurrentActionReward(((LeaderCardReward) leaderCard).getReward().getActionReward());
				this.player.getRoom().getGameHandler().setExpectedAction(((LeaderCardReward) leaderCard).getReward().getActionReward().getRequestedAction());
				this.player.getRoom().getGameHandler().sendGameUpdateExpectedAction(this.player, ((LeaderCardReward) leaderCard).getReward().getActionReward().createExpectedAction(this.player.getRoom().getGameHandler(), this.player));
				return;
			}
			int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
			if (councilPrivilegesCount > 0) {
				this.player.getRoom().getGameHandler().setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
				this.player.getRoom().getGameHandler().sendGameUpdateExpectedAction(this.player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
				return;
			}
		}
		this.player.getRoom().getGameHandler().setExpectedAction(null);
		this.player.getRoom().getGameHandler().sendGameUpdate(this.player);
	}
}
