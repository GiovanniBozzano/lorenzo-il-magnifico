package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsProductionStart;
import it.polimi.ingsw.lim.server.enums.WorkSlotType;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventStartProduction;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionProductionStart extends ActionInformationsProductionStart implements IAction
{
	private final Connection player;
	private int effectiveServants;
	private WorkSlotType workSlotType;

	public ActionProductionStart(Connection player, FamilyMemberType familyMemberType, int servants)
	{
		super(familyMemberType);
		this.player = player;
		this.effectiveServants = servants;
	}

	@Override
	public boolean isLegal()
	{
		// check if the players is inside a room
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return false;
		}
		// check if the game has started
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return false;
		}
		// check if the board slot is occupied and get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.getFamilyMemberType(), BoardPosition.PRODUCTION_SMALL, gameHandler.getFamilyMemberTypeValues().get(this.getFamilyMemberType()));
		IAction.applyModifiers(this.player.getPlayerInformations().getActiveModifiers(), eventPlaceFamilyMember);
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (!eventPlaceFamilyMember.isIgnoreOccupied()) {
			for (Connection player : room.getPlayers()) {
				if (player.getPlayerInformations().isOccupyingBoardPosition(BoardPosition.PRODUCTION_SMALL)) {
					this.workSlotType = WorkSlotType.BIG;
					break;
				}
			}
		}
		// check if the players has the servants he sent
		if (this.player.getPlayerInformations().getPlayerResourceHandler().getResourcesServant() < this.effectiveServants) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.effectiveServants);
		IAction.applyModifiers(this.player.getPlayerInformations().getActiveModifiers(), eventUseServants);
		this.effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		EventStartProduction eventStartProduction = new EventStartProduction(this.player, effectiveFamilyMemberValue + this.effectiveServants);
		IAction.applyModifiers(this.player.getPlayerInformations().getActiveModifiers(), eventStartProduction);
		return eventStartProduction.getActionValue() >= this.workSlotType.getCost();
	}

	@Override
	public void apply()
	{
	}

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}
}
