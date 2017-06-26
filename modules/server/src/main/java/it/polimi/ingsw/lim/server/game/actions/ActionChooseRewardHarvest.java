package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardHarvest;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardHarvest;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventHarvest;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseRewardHarvest extends ActionInformationsChooseRewardHarvest implements IAction
{
	private transient final Player player;

	public ActionChooseRewardHarvest(int servants, Player player)
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
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_HARVEST) {
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
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		List<ResourceAmount> resourceReward = new ArrayList<>();
		if (((ActionRewardHarvest) this.player.getCurrentActionReward()).isApplyModifiers()) {
			EventHarvest eventHarvest = new EventHarvest(this.player, ((ActionRewardHarvest) this.player.getCurrentActionReward()).getValue() + eventUseServants.getServants());
			eventHarvest.applyModifiers(this.player.getActiveModifiers());
			for (DevelopmentCardTerritory developmentCardTerritory : this.player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class)) {
				if (developmentCardTerritory.getActivationValue() > eventHarvest.getActionValue()) {
					continue;
				}
				resourceReward.addAll(developmentCardTerritory.getHarvestResources());
			}
		} else {
			for (DevelopmentCardTerritory developmentCardTerritory : this.player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class)) {
				if (developmentCardTerritory.getActivationValue() > ((ActionRewardHarvest) this.player.getCurrentActionReward()).getValue() + eventUseServants.getServants()) {
					continue;
				}
				resourceReward.addAll(developmentCardTerritory.getHarvestResources());
			}
		}
		resourceReward.addAll(this.player.getPersonalBonusTile().getHarvestInstantResources());
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.WORK);
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
}
