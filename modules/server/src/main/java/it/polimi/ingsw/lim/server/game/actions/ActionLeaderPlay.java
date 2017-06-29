package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
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
	private transient final Player player;
	private transient LeaderCard leaderCard;

	public ActionLeaderPlay(int leaderCardIndex, Player player)
	{
		super(leaderCardIndex);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			throw new GameActionFailedException("");
		}
		// check if the game has started
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			throw new GameActionFailedException("");
		}
		// check if it is the player's turn
		if (this.player != gameHandler.getTurnPlayer()) {
			throw new GameActionFailedException("");
		}
		// check whether the server expects the player to make this action
		if (gameHandler.getExpectedAction() != null) {
			throw new GameActionFailedException("");
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
			throw new GameActionFailedException("");
		}
		// check if the player's resources are enough
		for (LeaderCardConditionsOption leaderCardConditionsOption : this.leaderCard.getConditionsOptions()) {
			boolean availableConditionOption = true;
			if (leaderCardConditionsOption.getResourceAmounts() != null) {
				for (ResourceAmount requiredResources : leaderCardConditionsOption.getResourceAmounts()) {
					int playerResources = this.player.getPlayerResourceHandler().getResources().get(requiredResources.getResourceType());
					if (playerResources < requiredResources.getAmount()) {
						availableConditionOption = false;
						break;
					}
				}
			}
			if (leaderCardConditionsOption.getCardAmounts() != null) {
				for (CardAmount requiredCards : leaderCardConditionsOption.getCardAmounts()) {
					int playerCards = this.player.getPlayerCardHandler().getDevelopmentCardsNumber(requiredCards.getCardType());
					if (playerCards < requiredCards.getAmount()) {
						availableConditionOption = false;
						break;
					}
				}
			}
			if (availableConditionOption) {
				throw new GameActionFailedException("");
			}
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			throw new GameActionFailedException("");
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			throw new GameActionFailedException("");
		}
		gameHandler.setCurrentPhase(Phase.LEADER);
		// check Lollo
		if (this.leaderCard.getIndex() == 14) {
			gameHandler.sendGameUpdateExpectedAction(this.player, ((LeaderCardReward) this.leaderCard).getReward().getActionReward().createExpectedAction(gameHandler, this.player));
			return;
		}
		if (this.leaderCard instanceof LeaderCardModifier) {
			this.player.getActiveModifiers().add(((LeaderCardModifier) this.leaderCard).getModifier());
		} else {
			((LeaderCardReward) this.leaderCard).setActivated(true);
			EventGainResources eventGainResources = new EventGainResources(this.player, ((LeaderCardReward) this.leaderCard).getReward().getResourceAmounts(), ResourcesSource.LEADER_CARDS);
			eventGainResources.applyModifiers(this.player.getActiveModifiers());
			this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
			if (((LeaderCardReward) this.leaderCard).getReward().getActionReward() != null && ((LeaderCardReward) this.leaderCard).getReward().getActionReward().getRequestedAction() != null) {
				this.player.setCurrentActionReward(((LeaderCardReward) this.leaderCard).getReward().getActionReward());
				gameHandler.setExpectedAction(((LeaderCardReward) this.leaderCard).getReward().getActionReward().getRequestedAction());
				gameHandler.sendGameUpdateExpectedAction(this.player, ((LeaderCardReward) this.leaderCard).getReward().getActionReward().createExpectedAction(gameHandler, this.player));
				return;
			}
			int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
			if (councilPrivilegesCount > 0) {
				gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
				gameHandler.sendGameUpdateExpectedAction(this.player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
				return;
			}
		}
		gameHandler.sendGameUpdate(this.player);
	}
}
