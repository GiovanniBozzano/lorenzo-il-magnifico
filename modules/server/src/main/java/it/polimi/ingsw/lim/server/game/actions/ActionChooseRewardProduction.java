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
		return this.player.getPlayerInformations().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) >= this.servants;
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
		eventUseServants.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		EventStartProduction eventStartProduction = new EventStartProduction(this.player, ((ActionRewardProduction) this.player.getPlayerInformations().getCurrentActionReward()).getValue() + eventUseServants.getServants());
		eventStartProduction.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		this.player.getPlayerInformations().getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.servants);
		this.player.getPlayerInformations().getPlayerResourceHandler().addTemporaryResources(this.player.getPlayerInformations().getPersonalBonusTile().getHarvestInstantResources());
		// TODO aggiorno tutti
		this.player.getPlayerInformations().setCurrentProductionValue(eventStartProduction.getActionValue());
		gameHandler.setExpectedAction(ActionType.PRODUCTION_TRADE);
		// TODO mando azione trade
	}
}
