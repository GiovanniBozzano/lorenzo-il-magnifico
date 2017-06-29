package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardProductionStart;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionProductionTrade;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardProductionStart;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventProductionStart;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseRewardProductionStart extends ActionInformationsChooseRewardProductionStart implements IAction
{
	private transient final Player player;

	public ActionChooseRewardProductionStart(int servants, Player player)
	{
		super(servants);
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
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_PRODUCTION_START) {
			throw new GameActionFailedException("");
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.getServants()) {
			throw new GameActionFailedException("");
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
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.applyModifiers(this.player.getActiveModifiers());
		if (((ActionRewardProductionStart) this.player.getCurrentActionReward()).isApplyModifiers()) {
			EventProductionStart eventProductionStart = new EventProductionStart(this.player, ((ActionRewardProductionStart) this.player.getCurrentActionReward()).getValue() + eventUseServants.getServants());
			eventProductionStart.applyModifiers(this.player.getActiveModifiers());
			this.player.setCurrentProductionValue(eventProductionStart.getActionValue());
		} else {
			this.player.setCurrentProductionValue(((ActionRewardProductionStart) this.player.getCurrentActionReward()).getValue() + eventUseServants.getServants());
		}
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		EventGainResources eventGainResources = new EventGainResources(this.player, this.player.getPersonalBonusTile().getProductionInstantResources(), ResourcesSource.WORK);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		gameHandler.setExpectedAction(ActionType.PRODUCTION_TRADE);
		List<Integer> availableCards = new ArrayList<>();
		for (DevelopmentCardBuilding developmentCardBuilding : this.player.getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
			if (developmentCardBuilding.getActivationValue() <= this.player.getCurrentProductionValue()) {
				availableCards.add(developmentCardBuilding.getIndex());
			}
		}
		if (availableCards.isEmpty()) {
			int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
			if (councilPrivilegesCount > 0) {
				gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
				gameHandler.sendGameUpdateExpectedAction(this.player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
				return;
			}
			if (gameHandler.getCurrentPhase() == Phase.LEADER) {
				gameHandler.setExpectedAction(null);
				gameHandler.sendGameUpdate(this.player);
				return;
			}
			gameHandler.nextTurn();
		}
		gameHandler.sendGameUpdateExpectedAction(this.player, new ExpectedActionProductionTrade(availableCards));
	}
}
