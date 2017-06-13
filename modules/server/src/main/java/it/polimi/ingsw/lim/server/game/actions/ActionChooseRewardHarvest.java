package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardHarvest;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventStartHarvest;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseRewardHarvest implements IAction
{
	private final Connection player;
	private int servants;

	public ActionChooseRewardHarvest(Connection player, int servants)
	{
		this.player = player;
		this.servants = servants;
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
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_HARVEST) {
			return false;
		}
		// check if the player has the servants he sent
		return this.player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) >= this.servants;
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
		EventUseServants eventUseServants = new EventUseServants(this.player, this.servants);
		eventUseServants.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		EventStartHarvest eventStartHarvest = new EventStartHarvest(this.player, ((ActionRewardHarvest) this.player.getPlayerHandler().getCurrentActionReward()).getValue() + eventUseServants.getServants());
		eventStartHarvest.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		this.player.getPlayerHandler().getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.servants);
		List<ResourceAmount> resourceReward = new ArrayList<>();
		for (DevelopmentCardTerritory developmentCardTerritory : this.player.getPlayerHandler().getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class)) {
			if (developmentCardTerritory.getActivationValue() > eventStartHarvest.getActionValue()) {
				continue;
			}
			resourceReward.addAll(developmentCardTerritory.getHarvestResources());
		}
		resourceReward.addAll(this.player.getPlayerHandler().getPersonalBonusTile().getHarvestInstantResources());
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.WORK);
		eventGainResources.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		this.player.getPlayerHandler().getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		int councilPrivilegesCount = this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
			this.player.getPlayerHandler().getCouncilPrivileges().add(councilPrivilegesCount);
			gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
			// TODO aggiorno tutti
			// TODO manda scelta di privilegio
		} else {
			if (gameHandler.getPhase() == Phase.LEADER) {
				gameHandler.setExpectedAction(null);
				// TODO aggiorno tutti
				// TODO prosegui turno
			} else {
				gameHandler.nextTurn();
			}
		}
	}
}
