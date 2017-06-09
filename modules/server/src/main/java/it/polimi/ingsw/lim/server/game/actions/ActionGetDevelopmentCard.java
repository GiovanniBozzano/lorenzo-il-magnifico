package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
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
	private final int servants;
	private final CardType cardType;
	private final Row row;
	private ResourceCostOption resourceCostOption;
	private boolean columnOccupied = false;
	private List<ResourceAmount> effectiveResourceCost;

	public ActionGetDevelopmentCard(Connection player, FamilyMemberType familyMemberType, int servants, CardType cardType, Row row, ResourceCostOption resourceCostOption)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
		this.servants = servants;
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
		// check whether the server expects the player to make this action
		if (room.getGameHandler().getExpectedAction() != null) {
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
		DevelopmentCard developmentCard = room.getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.cardType).get(this.row);
		if (developmentCard == null) {
			return false;
		}
		// check whether you have another colored family member in the column
		if (this.familyMemberType != FamilyMemberType.NEUTRAL) {
			for (FamilyMemberType currentFamilyMemberType : this.player.getPlayerInformations().getFamilyMembersPositions().keySet()) {
				if (currentFamilyMemberType != FamilyMemberType.NEUTRAL && BoardPosition.getDevelopmentCardsColumnPositions(this.cardType).contains(this.player.getPlayerInformations().getFamilyMembersPositions().get(currentFamilyMemberType))) {
					return false;
				}
			}
		}
		// check if the board column is occupied
		for (Connection currentPlayer : room.getPlayers()) {
			if (this.columnOccupied) {
				break;
			}
			for (BoardPosition boardPosition : BoardPosition.getDevelopmentCardsColumnPositions(this.cardType)) {
				if (!currentPlayer.getPlayerInformations().isOccupyingBoardPosition(boardPosition)) {
					continue;
				}
				this.columnOccupied = true;
				break;
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerInformations().getPlayerResourceHandler().getResources(ResourceType.SERVANT) < this.servants) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.servants);
		eventUseServants.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		int effectiveServantsValue = eventUseServants.getServants();
		// controllo che la carta contenga il cost option
		if ((this.resourceCostOption == null && !developmentCard.getResourceCostOptions().isEmpty()) || (this.resourceCostOption != null && !developmentCard.getResourceCostOptions().contains(this.resourceCostOption))) {
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
		EventGetDevelopmentCard eventGetDevelopmentCard = new EventGetDevelopmentCard(this.player, this.cardType, this.row, this.resourceCostOption == null ? null : this.resourceCostOption.getResourcesAmount(), effectiveFamilyMemberValue + effectiveServantsValue);
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
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		DevelopmentCard developmentCard = room.getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.cardType).get(this.row);
		this.player.getPlayerInformations().getPlayerCardHandler().addDevelopmentCard(developmentCard);
		this.player.getPlayerInformations().getPlayerResourceHandler().subtractResource(new ResourceAmount(ResourceType.SERVANT, this.servants));
		this.player.getPlayerInformations().getPlayerResourceHandler().subtractResources(this.effectiveResourceCost);
		List<ResourceAmount> resourceReward = new ArrayList<>(developmentCard.getReward().getResourceAmounts());
		BoardPosition boardPosition = BoardPosition.getDevelopmentCardPosition(this.cardType, this.row);
		resourceReward.addAll(BoardHandler.BOARD_POSITIONS_INSTANT_REWARDS.get(boardPosition));
		this.player.getPlayerInformations().getPlayerResourceHandler().addResources(resourceReward);
		this.player.getPlayerInformations().getFamilyMembersPositions().put(this.familyMemberType, boardPosition);
		room.getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.cardType).put(this.row, null);
		// TODO aggiorno tutti
		if (developmentCard.getReward().getActionReward() != null) {
			room.getGameHandler().setExpectedAction(developmentCard.getReward().getActionReward().getRequestedAction());
			// TODO manda azione rimcompensa
		} else {
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

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}
}
