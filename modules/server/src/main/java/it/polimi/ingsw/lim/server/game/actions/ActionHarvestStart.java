package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.server.enums.WorkSlotType;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventStartHarvest;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;

public class ActionHarvestStart implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;
	private int servants;
	private WorkSlotType workSlotType;
	private int effectiveActionValue;

	public ActionHarvestStart(Connection player, FamilyMemberType familyMemberType, int servants)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
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
		if (gameHandler.getExpectedAction() != null) {
			return false;
		}
		// check if the board slot is occupied and get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.familyMemberType, BoardPosition.HARVEST_SMALL, gameHandler.getFamilyMemberTypeValues().get(this.familyMemberType));
		eventPlaceFamilyMember.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (!eventPlaceFamilyMember.isIgnoreOccupied()) {
			for (Connection currentPlayer : room.getPlayers()) {
				if (currentPlayer.getPlayerInformations().isOccupyingBoardPosition(BoardPosition.HARVEST_SMALL)) {
					this.workSlotType = WorkSlotType.BIG;
					break;
				}
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerInformations().getPlayerResourceHandler().getResources(ResourceType.SERVANT) < this.servants) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.servants);
		eventUseServants.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		int effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		EventStartHarvest eventStartHarvest = new EventStartHarvest(this.player, effectiveFamilyMemberValue + effectiveServants);
		eventStartHarvest.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		this.effectiveActionValue = eventStartHarvest.getActionValue();
		return this.effectiveActionValue >= (this.workSlotType == WorkSlotType.BIG ? BoardHandler.getBoardPositionInformations(BoardPosition.HARVEST_BIG).getValue() : BoardHandler.getBoardPositionInformations(BoardPosition.HARVEST_SMALL).getValue());
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
		this.player.getPlayerInformations().getPlayerResourceHandler().subtractResource(new ResourceAmount(ResourceType.SERVANT, this.servants));
		List<ResourceAmount> rewardResources = new ArrayList<>();
		for (DevelopmentCardTerritory developmentCardTerritory : this.player.getPlayerInformations().getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class)) {
			if (developmentCardTerritory.getActivationValue() > this.effectiveActionValue) {
				continue;
			}
			rewardResources.addAll(developmentCardTerritory.getHarvestResources());
		}
		rewardResources.addAll(this.player.getPlayerInformations().getPersonalBonusTile().getHarvestInstantResources());
		this.player.getPlayerInformations().getPlayerResourceHandler().addTemporaryResources(rewardResources);
		// TODO aggiorno tutti
		int councilPrivilegesCount = this.player.getPlayerInformations().getPlayerResourceHandler().getResources(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			room.getGameHandler().setExpectedAction(ActionType.CHOOSE_COUNCIL_PRIVILEGES_REWARDS);
			// TODO manda scelta di privilegio
		} else {
			room.getGameHandler().setExpectedAction(null);
			// TODO turno del prossimo giocatore
		}
	}
}
