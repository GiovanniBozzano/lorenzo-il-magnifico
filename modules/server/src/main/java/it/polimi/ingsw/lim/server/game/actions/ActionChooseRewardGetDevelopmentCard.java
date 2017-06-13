package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.cards.leaders.CardLeaderReward;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.utils.DiscountChoice;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseRewardGetDevelopmentCard implements IAction
{
	private final Connection player;
	private final int servants;
	private final CardType cardType;
	private final Row row;
	private final Row instantRewardRow;
	private final DiscountChoice discount;
	private ResourceCostOption resourceCostOption;
	private boolean columnOccupied = false;
	private boolean getBoardPositionReward = true;
	private List<ResourceAmount> effectiveResourceCost;

	public ActionChooseRewardGetDevelopmentCard(Connection player, int servants, CardType cardType, Row row, Row instantRewardRow, DiscountChoice discount, ResourceCostOption resourceCostOption)
	{
		this.player = player;
		this.servants = servants;
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
		// check whether the server expects the player to make this action
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_GET_DEVELOPMENT_CARD) {
			return false;
		}
		// check if the card is already taken
		DevelopmentCard developmentCard = gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.cardType).get(this.row);
		if (developmentCard == null) {
			return false;
		}
		// check if the player has developmentcard space available
		if (!this.player.getPlayerHandler().getPlayerCardHandler().canAddDevelopmentCard(this.cardType)) {
			return false;
		}
		if (this.instantRewardRow != Row.THIRD && this.instantRewardRow != Row.FOURTH) {
			return false;
		}
		// check if the board column is occupied
		for (Connection currentPlayer : room.getPlayers()) {
			for (BoardPosition boardPosition : BoardPosition.getDevelopmentCardsColumnPositions(this.cardType)) {
				if (currentPlayer.getPlayerHandler().isOccupyingBoardPosition(boardPosition)) {
					this.columnOccupied = true;
					break;
				}
			}
			if (this.columnOccupied) {
				break;
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.servants) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.servants);
		eventUseServants.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		int effectiveServantsValue = eventUseServants.getServants();
		// controllo che la carta contenga il cost option
		if ((this.resourceCostOption == null && !developmentCard.getResourceCostOptions().isEmpty()) || (this.resourceCostOption != null && !developmentCard.getResourceCostOptions().contains(this.resourceCostOption))) {
			return false;
		}
		if (this.columnOccupied) {
			if (this.resourceCostOption == null) {
				List<ResourceAmount> requiredResources = new ArrayList<>();
				List<ResourceAmount> spentResources = new ArrayList<>();
				spentResources.add(new ResourceAmount(ResourceType.COIN, 3));
				this.resourceCostOption = new ResourceCostOption(requiredResources, spentResources);
			} else {
				this.resourceCostOption.getSpentResources().add(new ResourceAmount(ResourceType.COIN, 3));
			}
		}
		// check if the player has the requiredResources
		if (this.resourceCostOption.getRequiredResources() != null) {
			for (ResourceAmount requiredResources : this.resourceCostOption.getRequiredResources()) {
				int playerResources = this.player.getPlayerHandler().getPlayerResourceHandler().getResources().get(requiredResources.getResourceType());
				if (playerResources < requiredResources.getAmount()) {
					return false;
				}
			}
		}
		// check if the family member and servants value is high enough
		EventGetDevelopmentCard eventGetDevelopmentCard = new EventGetDevelopmentCard(this.player, this.cardType, this.row, this.resourceCostOption == null ? null : this.resourceCostOption.getSpentResources(), ((ActionRewardGetDevelopmentCard) this.player.getPlayerHandler().getCurrentActionReward()).getValue() + effectiveServantsValue);
		eventGetDevelopmentCard.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		this.getBoardPositionReward = eventGetDevelopmentCard.isGetBoardPositionReward();
		// if the card is a territory one, check whether the player has enough military points
		if (developmentCard.getCardType() == CardType.TERRITORY && !eventGetDevelopmentCard.isIgnoreTerritoriesSlotLock() && !this.player.getPlayerHandler().getPlayerResourceHandler().isTerritorySlotAvailable(this.player.getPlayerHandler().getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class).size())) {
			return false;
		}
		// controlla presenza discountchoice nell'array actionreward
		if ((this.discount == null && !((ActionRewardGetDevelopmentCard) this.player.getPlayerHandler().getCurrentActionReward()).getDiscountChoices().isEmpty()) || (this.discount != null && !((ActionRewardGetDevelopmentCard) this.player.getPlayerHandler().getCurrentActionReward()).getDiscountChoices().contains(this.discount))) {
			return false;
		}
		this.effectiveResourceCost = eventGetDevelopmentCard.getResourceCost();
		// getcardprice e togli discountchoice
		if (this.discount != null) {
			for (ResourceAmount resourceCost : this.effectiveResourceCost) {
				for (ResourceAmount resourceDiscount : this.discount.getResourceAmounts()) {
					if (resourceCost.getResourceType() == resourceDiscount.getResourceType()) {
						resourceCost.setAmount(resourceCost.getAmount() - resourceDiscount.getAmount());
					}
				}
			}
		}
		// prendo prezzo finale e controllo che il giocatore abbia le risorse necessarie
		for (ResourceAmount resourceCost : this.effectiveResourceCost) {
			int playerResources = this.player.getPlayerHandler().getPlayerResourceHandler().getResources().get(resourceCost.getResourceType());
			if (playerResources < resourceCost.getAmount()) {
				return false;
			}
		}
		return eventGetDevelopmentCard.getActionValue() >= BoardHandler.getBoardPositionInformations(BoardPosition.getDevelopmentCardPosition(this.cardType, this.row)).getValue();
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
		DevelopmentCard developmentCard = gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.cardType).get(this.row);
		this.player.getPlayerHandler().getPlayerCardHandler().addDevelopmentCard(developmentCard);
		this.player.getPlayerHandler().getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.servants);
		this.player.getPlayerHandler().getPlayerResourceHandler().subtractResources(this.effectiveResourceCost);
		List<ResourceAmount> resourceReward = new ArrayList<>(developmentCard.getReward().getResourceAmounts());
		if (this.getBoardPositionReward) {
			resourceReward.addAll(BoardHandler.getBoardPositionInformations(BoardPosition.getDevelopmentCardPosition(this.cardType, this.row)).getResourceAmounts());
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.DEVELOPMENT_CARDS);
		eventGainResources.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		this.player.getPlayerHandler().getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.cardType).put(this.row, null);
		if (developmentCard.getReward().getActionReward() != null) {
			this.player.getPlayerHandler().setCurrentActionReward(developmentCard.getReward().getActionReward());
			gameHandler.setExpectedAction(developmentCard.getReward().getActionReward().getRequestedAction());
			// TODO aggiorno tutti
			// TODO manda azione rimcompensa
		} else {
			int councilPrivilegesCount = this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
			if (councilPrivilegesCount > 0) {
				this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
				this.player.getPlayerHandler().getCouncilPrivileges().add(councilPrivilegesCount);
				gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
				// TODO aggiorno tutti
				// TODO manda scelta di privilegio
			} else {
				if (gameHandler.getPhase() == Phase.LEADER) {
					gameHandler.setExpectedAction(null);
					// TODO aggiorno tutti
					// TODO prosegui turno
				} else {
					gameHandler.nextTurn();
				}
			}
		}
	}
}