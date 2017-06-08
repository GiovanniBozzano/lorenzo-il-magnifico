package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;

public class ActionGetDevelopmentCard implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;
	private int effectiveServants;
	private final CardType cardType;
	private final Row row;
	private ResourceCostOption resourceCostOption;
	private boolean columnOccupied = false;

	public ActionGetDevelopmentCard(Connection player, FamilyMemberType familyMemberType, int effectiveServants, CardType cardType, Row row, ResourceCostOption resourceCostOption)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
		this.effectiveServants = effectiveServants;
		this.cardType = cardType;
		this.row = row;
		this.resourceCostOption = resourceCostOption;
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
		// check if the player has developmentcard space available
		if (!this.player.getPlayerInformations().getPlayerCardHandler().canAddDevelopmentCard(this.cardType)) {
			return false;
		}
		// check if the card is already taken and get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.familyMemberType, BoardPosition.getDevelopmentCardPosition(this.cardType, this.row), gameHandler.getFamilyMemberTypeValues().get(this.familyMemberType));
		eventPlaceFamilyMember.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (room.getGameHandler().getCardsHandler().getDevelopmentCard(this.cardType, this.row) == null) {
			return false;
		}
		// check if the board column is occupied
		for (Connection currentPlayer : room.getPlayers()) {
			for (BoardPosition boardPosition : BoardPosition.getDevelopmentCardsColumnPositions(this.cardType)) {
				if (currentPlayer.getPlayerInformations().getFamilyMembersPositions().get(this.familyMemberType) == boardPosition) {
					return false;
				}
				if (currentPlayer.getPlayerInformations().isOccupyingBoardPosition(boardPosition)) {
					this.columnOccupied = true;
				}
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerInformations().getPlayerResourceHandler().getResources(ResourceType.SERVANT) < this.effectiveServants) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.effectiveServants);
		eventUseServants.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		this.effectiveServants = eventUseServants.getServants();
		// controllo che la carta contenga il cost option
		if ((this.resourceCostOption == null && !room.getGameHandler().getCardsHandler().getDevelopmentCard(this.cardType, this.row).getResourceCostOptions().isEmpty()) || (this.resourceCostOption != null && !room.getGameHandler().getCardsHandler().getDevelopmentCard(this.cardType, this.row).getResourceCostOptions().contains(this.resourceCostOption))) {
			return false;
		}
		if (this.columnOccupied) {
			if (this.resourceCostOption == null) {
				List<ResourceAmount> resourcesAmount = new ArrayList<>();
				resourcesAmount.add(new ResourceAmount(ResourceType.COIN, 3));
				this.resourceCostOption = new ResourceCostOption(resourcesAmount);
			} else {
				this.resourceCostOption.getResourcesAmount().add(new ResourceAmount(ResourceType.COIN, 3));
			}
		}
		// check if the family member and servants value is high enough
		EventGetDevelopmentCard eventGetDevelopmentCard = new EventGetDevelopmentCard(this.player, this.cardType, this.row, this.resourceCostOption == null ? null : this.resourceCostOption.getResourcesAmount(), effectiveFamilyMemberValue + this.effectiveServants);
		eventGetDevelopmentCard.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		// prendo prezzo finale e controllo che il giocatore abbia le risorse necessarie
		for (ResourceAmount resourceCost : eventGetDevelopmentCard.getResourceCost()) {
			int playerResources = this.player.getPlayerInformations().getPlayerResourceHandler().getResources(resourceCost.getResourceType());
			if (playerResources < resourceCost.getAmount()) {
				return false;
			}
		}
		return eventGetDevelopmentCard.getActionValue() >= this.row.getValue();
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
