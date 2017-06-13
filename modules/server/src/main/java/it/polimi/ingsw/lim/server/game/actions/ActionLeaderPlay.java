package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.cards.CardLeader;
import it.polimi.ingsw.lim.server.game.cards.leaders.CardLeaderModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.CardLeaderReward;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionLeaderPlay implements IAction
{
	private final Connection player;
	private final int cardLeaderIndex;
	private CardLeader cardLeader;

	public ActionLeaderPlay(Connection player, int cardLeaderIndex)
	{
		this.player = player;
		this.cardLeaderIndex = cardLeaderIndex;
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
		for (CardLeader currentCardLeader : this.player.getPlayerHandler().getPlayerCardHandler().getCardsLeader()) {
			if (this.cardLeaderIndex == currentCardLeader.getIndex()) {
				this.cardLeader = currentCardLeader;
				owned = true;
				break;
			}
		}
		if (!owned) {
			return false;
		}
		// check if the player's resources are enough
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
		gameHandler.setPhase(Phase.LEADER);
		if (this.cardLeader instanceof CardLeaderModifier) {
			this.player.getPlayerHandler().getActiveModifiers().add(((CardLeaderModifier) this.cardLeader).getModifier());
		} else {
			((CardLeaderReward) this.cardLeader).setActivated(true);
			EventGainResources eventGainResources = new EventGainResources(this.player, ((CardLeaderReward) this.cardLeader).getReward().getResourceAmounts(), ResourcesSource.LEADER_CARDS);
			eventGainResources.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
			this.player.getPlayerHandler().getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
			if (((CardLeaderReward) this.cardLeader).getReward().getActionReward() != null) {
				((CardLeaderReward) this.cardLeader).getReward().getActionReward().apply(this.player);
			}
			int councilPrivilegesCount = this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
			if (councilPrivilegesCount > 0) {
				this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
				this.player.getPlayerHandler().getCouncilPrivileges().add(councilPrivilegesCount);
				gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
				// TODO aggiorno tutti
				// TODO manda scelta di privilegio
				return;
			}
		}
		// TODO aggiorno tutti
		// TODO prosegui turno
	}
}
