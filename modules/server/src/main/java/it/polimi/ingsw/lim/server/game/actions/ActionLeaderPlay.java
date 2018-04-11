package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationLeaderPlay;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

public class ActionLeaderPlay extends ActionInformationLeaderPlay implements IAction
{
	private final transient Player player;

	public ActionLeaderPlay(int leaderCardIndex, Player player)
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
			if (this.getLeaderCardIndex() == currentLeaderCard.getIndex()) {
				leaderCard = currentLeaderCard;
				break;
			}
		}
		if (leaderCard == null) {
			throw new GameActionFailedException("You do not have this Leader Card");
		}
		// check if the player's resources are enough
		if (leaderCard.getConditionsOptions().isEmpty()) {
			return;
		}
		if (leaderCard instanceof LeaderCardReward && ((LeaderCardReward) leaderCard).getReward().getActionReward() != null && ((LeaderCardReward) leaderCard).getReward().getActionReward().getRequestedAction() == ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER) {
			boolean availableCardsToCopy = false;
			for (Player otherPlayer : this.player.getRoom().getGameHandler().getTurnOrder()) {
				if (otherPlayer != this.player) {
					for (LeaderCard currentLeaderCard : otherPlayer.getPlayerCardHandler().getLeaderCards()) {
						if (currentLeaderCard.isPlayed()) {
							availableCardsToCopy = true;
							break;
						}
					}
				}
				if (availableCardsToCopy) {
					break;
				}
			}
			if (!availableCardsToCopy) {
				throw new GameActionFailedException("No other Leader Cards are available");
			}
		}
		for (LeaderCardConditionsOption leaderCardConditionsOption : leaderCard.getConditionsOptions()) {
			boolean availableConditionOption = true;
			if (leaderCardConditionsOption.getResourceAmounts() != null && !this.player.getPlayerResourceHandler().canAffordResources(leaderCardConditionsOption.getResourceAmounts())) {
				availableConditionOption = false;
			}
			if (availableConditionOption && leaderCardConditionsOption.getDevelopmentCardAmounts() != null && !this.player.getPlayerCardHandler().hasEnoughCards(leaderCardConditionsOption.getDevelopmentCardAmounts())) {
				availableConditionOption = false;
			}
			if (availableConditionOption) {
				return;
			}
		}
		throw new GameActionFailedException("You do not have the necessary resources to perform this action");
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.LEADER);
		LeaderCard leaderCard = null;
		for (LeaderCard currentLeaderCard : this.player.getPlayerCardHandler().getLeaderCards()) {
			if (this.getLeaderCardIndex() == currentLeaderCard.getIndex()) {
				leaderCard = currentLeaderCard;
				break;
			}
		}
		if (leaderCard == null) {
			throw new GameActionFailedException("You do not have this Leader Card");
		}
		if (leaderCard instanceof LeaderCardReward && ((LeaderCardReward) leaderCard).getReward().getActionReward() != null && ((LeaderCardReward) leaderCard).getReward().getActionReward().getRequestedAction() == ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER) {
			this.player.setCurrentActionReward(((LeaderCardReward) leaderCard).getReward().getActionReward());
			this.player.getRoom().getGameHandler().setExpectedAction(((LeaderCardReward) leaderCard).getReward().getActionReward().getRequestedAction());
			this.player.getRoom().getGameHandler().sendGameUpdateExpectedAction(this.player, ((LeaderCardReward) leaderCard).getReward().getActionReward().createExpectedAction(this.player));
			return;
		}
		leaderCard.setPlayed(true);
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " played his leader card " + leaderCard.getDisplayName());
		if (Utils.activateLeaderCard(this.player, leaderCard)) {
			return;
		}
		player.getPlayerResourceHandler().convertTemporaryResources();
		this.player.getRoom().getGameHandler().sendGameUpdate(this.player);
	}
}
