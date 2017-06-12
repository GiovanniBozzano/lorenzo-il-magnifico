package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardProduction;
import it.polimi.ingsw.lim.server.game.events.EventStartProduction;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionChooseRewardProduction implements IAction
{
	private final Connection player;
	private int servants;

	public ActionChooseRewardProduction(Connection player, int servants)
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
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_PRODUCTION) {
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
		EventStartProduction eventStartProduction = new EventStartProduction(this.player, ((ActionRewardProduction) this.player.getPlayerHandler().getCurrentActionReward()).getValue() + eventUseServants.getServants());
		eventStartProduction.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		this.player.getPlayerHandler().getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.servants);
		this.player.getPlayerHandler().getPlayerResourceHandler().addTemporaryResources(this.player.getPlayerHandler().getPersonalBonusTile().getHarvestInstantResources());
		this.player.getPlayerHandler().setCurrentProductionValue(eventStartProduction.getActionValue());
		gameHandler.setExpectedAction(ActionType.PRODUCTION_TRADE);
		// TODO mando azione trade
	}
}
