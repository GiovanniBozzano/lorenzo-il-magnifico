package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.utils.DiscountChoice;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionChooseRewardGetDevelopmentCard implements IAction
{
	private final Connection player;
	private int effectiveServants;
	private final CardType cardType;
	private final Row row;
	private final Row instantRewardRow;
	private final DiscountChoice discount;
	private final ResourceCostOption resourceCostOption;

	public ActionChooseRewardGetDevelopmentCard(Connection player, int effectiveServants, CardType cardType, Row row, Row instantRewardRow, DiscountChoice discount, ResourceCostOption resourceCostOption)
	{
		this.player = player;
		this.effectiveServants = effectiveServants;
		this.cardType = cardType;
		this.row = row;
		this.instantRewardRow = instantRewardRow;
		this.discount = discount;
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
		// check if the board slot is occupied and get actionTypevalue
		for (Connection currentPlayer : room.getPlayers()) {
			if (currentPlayer.getPlayerInformations().isOccupyingBoardPosition(BoardPosition.getDevelopmentCardPosition(this.cardType, this.row))) {
				return false;
			}
		}
		// check if the board column is occupied
		// check if the player has developmentcard space available
		boolean space = false;
		switch (this.cardType) {
			case BUILDING:
				if (this.player.getPlayerInformations().getPlayerCardHandler().canAddDevelopmentCardBuilding()) {
					space = true;
				}
				break;
			case CHARACTER:
				if (this.player.getPlayerInformations().getPlayerCardHandler().canAddDevelopmentCardCharacter()) {
					space = true;
				}
				break;
			case TERRITORY:
				if (this.player.getPlayerInformations().getPlayerCardHandler().canAddDevelopmentCardTerritory()) {
					space = true;
				}
				break;
			case VENTURE:
				if (this.player.getPlayerInformations().getPlayerCardHandler().canAddDevelopmentCardVenture()) {
					space = true;
				}
		}
		if (!space) {
			return false;
		}        // check if the player has the servants he sent
		if (this.player.getPlayerInformations().getPlayerResourceHandler().getResources(ResourceType.SERVANT) < this.effectiveServants) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.effectiveServants);
		eventUseServants.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		this.effectiveServants = eventUseServants.getServants();
		// controllo che la carta contenga il cost option
		if (room.getGameHandler().getCardsHandler().getDevelopmentCard(cardType, row).getResourceCostOptions().contains(this.resourceCostOption)) {
			return false;
		}
		// check if the family member and servants value is high enough
		EventGetDevelopmentCard eventGetDevelopmentCard = new EventGetDevelopmentCard(this.player, this.cardType, this.row, resourceCostOption.getResourcesAmount(), ((ActionRewardGetDevelopmentCard) this.player.getPlayerInformations().getCurrentActionReward()).getValue() + this.effectiveServants);
		eventGetDevelopmentCard.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		// controlla presenza discountchoice nell'array actionreward
		if ((discount == null && !((ActionRewardGetDevelopmentCard) this.player.getPlayerInformations().getCurrentActionReward()).getDiscountChoices().isEmpty()) || (this.discount != null && !((ActionRewardGetDevelopmentCard) this.player.getPlayerInformations().getCurrentActionReward()).getDiscountChoices().contains(this.discount))) {
			return false;
		}
		// getcardprice e togli discountchoice
		if (discount != null) {
			for (ResourceAmount resourceCost : eventGetDevelopmentCard.getCost()) {
				for (ResourceAmount resourceDiscount : discount.getResourceAmounts()) {
					if (resourceCost.getResourceType() == resourceDiscount.getResourceType()) {
						resourceCost.setAmount(resourceCost.getAmount() - resourceDiscount.getAmount());
					}
				}
			}
		}
		// prendo prezzo finale e controllo che il giocatore abbia le risorse necessarie
		for (ResourceAmount resourceCost : eventGetDevelopmentCard.getCost()) {
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

	public int getServants()
	{
		return this.effectiveServants;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public Row getRow()
	{
		return this.row;
	}

	public Row getInstantRewardRow()
	{
		return this.instantRewardRow;
	}
}
