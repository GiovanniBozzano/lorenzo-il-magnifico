package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderActivate;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

public class ActionLeaderActivate extends ActionInformationsLeaderActivate implements IAction
{
	private final transient Player player;

	public ActionLeaderActivate(int leaderCardIndex, Player player)
	{
		super(leaderCardIndex);
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
		if (this.player.getRoom().getGameHandler().getExpectedAction() != null) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check if the player has the leader card
		LeaderCard leaderCard = null;
		for (LeaderCard currentLeaderCard : this.player.getPlayerCardHandler().getLeaderCards()) {
			if (this.getLeaderCardIndex() == currentLeaderCard.getIndex() && currentLeaderCard instanceof LeaderCardReward) {
				leaderCard = currentLeaderCard;
				break;
			}
		}
		if (leaderCard == null) {
			throw new GameActionFailedException("You do not have this Leader Card");
		}
		// check if the leader card has been played
		if (!leaderCard.isPlayed()) {
			throw new GameActionFailedException("This Leader Card has not been played yet");
		}
		// check if the leader card hasn't been already activated
		if (((LeaderCardReward) leaderCard).isActivated()) {
			throw new GameActionFailedException("This Leader Card has already been activated");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		LeaderCard leaderCard = null;
		for (LeaderCard currentLeaderCard : this.player.getPlayerCardHandler().getLeaderCards()) {
			if (this.getLeaderCardIndex() == currentLeaderCard.getIndex() && currentLeaderCard instanceof LeaderCardReward) {
				leaderCard = currentLeaderCard;
				break;
			}
		}
		if (leaderCard == null) {
			throw new GameActionFailedException("You do not have this Leader Card");
		}
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.LEADER);
		((LeaderCardReward) leaderCard).setActivated(true);
		EventGainResources eventGainResources = new EventGainResources(this.player, ((LeaderCardReward) leaderCard).getReward().getResourceAmounts(), ResourcesSource.LEADER_CARDS);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " activated his leader card " + leaderCard.getDisplayName());
		if (Utils.sendActionReward(this.player, ((LeaderCardReward) leaderCard).getReward().getActionReward())) {
			return;
		}
		if (Utils.sendCouncilPrivileges(this.player)) {
			return;
		}
		this.player.getPlayerResourceHandler().convertTemporaryResources();
		this.player.getRoom().getGameHandler().sendGameUpdate(this.player);
	}
}
