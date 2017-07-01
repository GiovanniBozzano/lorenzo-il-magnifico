package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardProductionStart;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardProductionStart;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventProductionStart;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.utils.Utils;

public class ActionChooseRewardProductionStart extends ActionInformationsChooseRewardProductionStart implements IAction
{
	private final transient Player player;

	public ActionChooseRewardProductionStart(int servants, Player player)
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
		if (this.player.getRoom().getGameHandler().getExpectedAction() != ActionType.CHOOSE_REWARD_PRODUCTION_START) {
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
		this.player.getRoom().getGameHandler().setExpectedAction(ActionType.PRODUCTION_TRADE);
		Utils.checkAvailableProductionCards(this.player);
	}
}
