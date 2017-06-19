package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardProduction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionProductionTrade;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardProduction;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventStartProduction;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseRewardProduction extends ActionInformationsChooseRewardProduction implements IAction
{
	private final Player player;
	private int effectiveActionValue;

	public ActionChooseRewardProduction(int servants, Player player)
	{
		super(servants);
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
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_PRODUCTION) {
			return false;
		}
		// check if the player has the servants he sent
		return this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) >= this.getServants();
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
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.applyModifiers(this.player.getActiveModifiers());
		if (((ActionRewardProduction) this.player.getCurrentActionReward()).isApplyModifiers()) {
			EventStartProduction eventStartProduction = new EventStartProduction(this.player, ((ActionRewardProduction) this.player.getCurrentActionReward()).getValue() + eventUseServants.getServants());
			eventStartProduction.applyModifiers(this.player.getActiveModifiers());
			this.player.setCurrentProductionValue(eventStartProduction.getActionValue());
		} else {
			this.player.setCurrentProductionValue(((ActionRewardProduction) this.player.getCurrentActionReward()).getValue() + eventUseServants.getServants());
		}
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		gameHandler.setExpectedAction(ActionType.PRODUCTION_TRADE);
		List<Integer> availableCards = new ArrayList<>();
		for (DevelopmentCardBuilding developmentCardBuilding : this.player.getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
			if (developmentCardBuilding.getActivationValue() <= this.effectiveActionValue) {
				availableCards.add(developmentCardBuilding.getIndex());
			}
		}
		if (availableCards.isEmpty()) {
			EventGainResources eventGainResources = new EventGainResources(this.player, this.player.getPersonalBonusTile().getProductionInstantResources(), ResourcesSource.WORK);
			eventGainResources.applyModifiers(this.player.getActiveModifiers());
			this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
			int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
			if (councilPrivilegesCount > 0) {
				this.player.getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
				this.player.getCouncilPrivileges().add(councilPrivilegesCount);
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
