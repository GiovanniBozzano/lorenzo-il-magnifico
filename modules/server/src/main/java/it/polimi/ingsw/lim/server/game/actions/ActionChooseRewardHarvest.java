package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardHarvest;
import it.polimi.ingsw.lim.server.game.events.EventStartHarvest;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionChooseRewardHarvest implements IAction
{
	private final Connection player;
	private int effectiveServants;

	public ActionChooseRewardHarvest(Connection player, int effectiveServants)
	{
		this.player = player;
		this.effectiveServants = effectiveServants;
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
		if (this.player.getPlayerInformations().getPlayerResourceHandler().getResources(ResourceType.SERVANT) < this.effectiveServants) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.effectiveServants);
		eventUseServants.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		this.effectiveServants = eventUseServants.getServants();
		// check if the action value and servants value is high enough
		EventStartHarvest eventStartHarvest = new EventStartHarvest(this.player, ((ActionRewardHarvest) this.player.getPlayerInformations().getCurrentActionReward()).getValue() + this.effectiveServants);
		eventStartHarvest.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		return eventStartHarvest.getActionValue() >= 1;
	}

	@Override
	public void apply()
	{
	}
}
