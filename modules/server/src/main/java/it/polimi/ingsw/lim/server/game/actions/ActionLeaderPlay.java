package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderPlay;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.CardAmount;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

public class ActionLeaderPlay extends ActionInformationsLeaderPlay implements IAction
{
	private final Player player;
	private LeaderCard leaderCard;

	public ActionLeaderPlay(int leaderCardIndex, LeaderCardConditionsOption leaderCardConditionsOption, Player player)
	{
		super(leaderCardIndex, leaderCardConditionsOption);
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
		// check if the player has the leader card
		boolean owned = false;
		for (LeaderCard currentLeaderCard : this.player.getPlayerCardHandler().getLeaderCards()) {
			if (this.getLeaderCardIndex() == currentLeaderCard.getIndex()) {
				this.leaderCard = currentLeaderCard;
				owned = true;
				break;
			}
		}
		if (!owned) {
			return false;
		}
		// check if the player's resources are enough
		if (this.getLeaderCardConditionsOption().getResourceAmounts() != null) {
			for (ResourceAmount requiredResources : this.getLeaderCardConditionsOption().getResourceAmounts()) {
				int playerResources = this.player.getPlayerResourceHandler().getResources().get(requiredResources.getResourceType());
				if (playerResources < requiredResources.getAmount()) {
					return false;
				}
			}
		}
		if (this.getLeaderCardConditionsOption().getCardAmounts() != null) {
			for (CardAmount requiredCards : this.getLeaderCardConditionsOption().getCardAmounts()) {
				int playerCards = this.player.getPlayerCardHandler().getDevelopmentCardsNumber(requiredCards.getCardType());
				if (playerCards < requiredCards.getAmount()) {
					return false;
				}
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
		gameHandler.setCurrentPhase(Phase.LEADER);
		// check Lollo
		if (this.leaderCard.getIndex() == 14) {
			gameHandler.sendGameUpdateExpectedAction(this.player, ((LeaderCardReward) this.leaderCard).getReward().getActionReward().createExpectedAction(gameHandler,this.player));
			return;
		}
		if (this.leaderCard instanceof LeaderCardModifier) {
			this.player.getActiveModifiers().add(((LeaderCardModifier) this.leaderCard).getModifier());
		} else {
			((LeaderCardReward) this.leaderCard).setActivated(true);
			EventGainResources eventGainResources = new EventGainResources(this.player, ((LeaderCardReward) this.leaderCard).getReward().getResourceAmounts(), ResourcesSource.LEADER_CARDS);
			eventGainResources.applyModifiers(this.player.getActiveModifiers());
			this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
			if (((LeaderCardReward) this.leaderCard).getReward().getActionReward() != null) {
				this.player.setCurrentActionReward(((LeaderCardReward) this.leaderCard).getReward().getActionReward());
				gameHandler.setExpectedAction(((LeaderCardReward) this.leaderCard).getReward().getActionReward().getRequestedAction());
				gameHandler.sendGameUpdateExpectedAction(this.player, ((LeaderCardReward) this.leaderCard).getReward().getActionReward().createExpectedAction(gameHandler, this.player));
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
		gameHandler.sendGameUpdate(this.player);
	}
}
