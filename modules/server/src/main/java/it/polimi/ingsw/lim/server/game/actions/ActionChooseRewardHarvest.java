package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardHarvest;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardHarvest;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventHarvest;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseRewardHarvest extends ActionInformationsChooseRewardHarvest implements IAction
{
	private final transient Player player;

	public ActionChooseRewardHarvest(int servants, Player player)
	{
		super(servants);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if it is the player's turn
		if (this.player != this.player.getRoom().getGameHandler().getTurnPlayer()) {
			throw new GameActionFailedException("It's not this player's turn");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != ActionType.CHOOSE_REWARD_HARVEST) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.getServants()) {
			throw new GameActionFailedException("Player doesn't have the number of servants he wants to use");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
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
		if (Utils.sendCouncilPrivileges(this.player)) {
			return;
		}
		if (this.player.getRoom().getGameHandler().getCurrentPhase() == Phase.LEADER) {
			this.player.getRoom().getGameHandler().setExpectedAction(null);
			this.player.getRoom().getGameHandler().sendGameUpdate(this.player);
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
