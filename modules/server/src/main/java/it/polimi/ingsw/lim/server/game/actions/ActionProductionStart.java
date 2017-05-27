package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsProductionStart;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.events.EventStartProduction;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionProductionStart extends ActionInformationsProductionStart implements IAction
{
	private final Connection player;
	private final int servants;
	private EventStartProduction event;

	public ActionProductionStart(Connection player, FamilyMemberType familyMemberType, int servants)
	{
		super(familyMemberType);
		this.player = player;
		this.servants = servants;
	}

	@Override
	public boolean isLegal()
	{
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return false;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return false;
		}
		if (this.player.getPlayerInformations().getPlayerResourceHandler().getResourcesServant() < this.servants) {
			return false;
		}
		this.event = new EventStartProduction(gameHandler.getFamilyMemberTypeValues().get(this.getFamilyMemberType()) + this.servants);
		IAction.applyBonuses(this.player.getPlayerInformations().getActiveBonuses(), this.event);
		return this.event.getValue() >= gameHandler.getFreeProductionSlotType().getCost();
	}

	@Override
	public void apply()
	{
		this.event.apply();
	}

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}
}
