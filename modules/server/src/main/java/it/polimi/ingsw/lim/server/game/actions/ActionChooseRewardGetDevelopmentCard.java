package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardCharacter;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.utils.Phase;
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
	private final List<ResourceAmount> instantDiscountChoice;
	private final List<ResourceAmount> discountChoice;
	private ResourceCostOption resourceCostOption;
	private boolean columnOccupied = false;
	private boolean getBoardPositionReward = true;
	private List<ResourceAmount> effectiveResourceCost;

	public ActionChooseRewardGetDevelopmentCard(Connection player, int servants, CardType cardType, Row row, Row instantRewardRow, List<ResourceAmount> instantDiscountChoice, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption)
	{
		this.player = player;
		this.servants = servants;
		this.cardType = cardType;
		this.row = row;
		this.instantRewardRow = instantRewardRow;
		this.instantDiscountChoice = instantDiscountChoice;
		this.discountChoice = discountChoice;
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
		EventGetDevelopmentCard eventGetDevelopmentCard = new EventGetDevelopmentCard(this.player, this.cardType, this.row, this.resourceCostOption == null ? null : this.resourceCostOption.getSpentResources(), BoardHandler.getBoardPositionInformations(BoardPosition.getDevelopmentCardPosition(cardType, row)).getValue() + effectiveServantsValue);
		eventGetDevelopmentCard.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		this.effectiveResourceCost = eventGetDevelopmentCard.getResourceCost();
		this.getBoardPositionReward = eventGetDevelopmentCard.isGetBoardPositionReward();
		// if the card is a territory one, check whether the player has enough military points
		if (developmentCard.getCardType() == CardType.TERRITORY && !eventGetDevelopmentCard.isIgnoreTerritoriesSlotLock() && !this.player.getPlayerHandler().getPlayerResourceHandler().isTerritorySlotAvailable(this.player.getPlayerHandler().getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class).size())) {
			return false;
		}
		// controlla presenza discountchoice nell'array actionreward
		if ((this.instantDiscountChoice == null && !((ActionRewardGetDevelopmentCard) this.player.getPlayerHandler().getCurrentActionReward()).getInstantDiscountChoices().isEmpty()) || (this.instantDiscountChoice != null && !((ActionRewardGetDevelopmentCard) this.player.getPlayerHandler().getCurrentActionReward()).getInstantDiscountChoices().contains(this.instantDiscountChoice))) {
			return false;
		}
		if (this.resourceCostOption == null && this.discountChoice != null) {
			return false;
		}
		if (this.resourceCostOption != null) {
			if (this.discountChoice == null) {
				boolean validDiscountChoice = true;
				for (Modifier modifier : this.player.getPlayerHandler().getActiveModifiers()) {
					if (modifier.getEventClass() == EventGetDevelopmentCard.class && !((ModifierGetDevelopmentCard) modifier).getDiscountChoices().isEmpty()) {
						validDiscountChoice = false;
						break;
					}
				}
				if (!validDiscountChoice) {
					return false;
				}
			} else {
				boolean validDiscountChoice = false;
				for (Modifier modifier : this.player.getPlayerHandler().getActiveModifiers()) {
					if (modifier.getEventClass() == EventGetDevelopmentCard.class && ((ModifierGetDevelopmentCard) modifier).getDiscountChoices().contains(this.discountChoice)) {
						validDiscountChoice = true;
						break;
					}
				}
				if (!validDiscountChoice) {
					return false;
				}
			}
		}
		// getcardprice e togli discountchoice
		if (this.instantDiscountChoice != null) {
			for (ResourceAmount resourceCost : this.effectiveResourceCost) {
				for (ResourceAmount resourceDiscount : this.instantDiscountChoice) {
					if (resourceCost.getResourceType() == resourceDiscount.getResourceType()) {
						resourceCost.setAmount(resourceCost.getAmount() - resourceDiscount.getAmount());
					}
				}
			}
		}
		if (this.discountChoice != null) {
			for (ResourceAmount resourceCost : this.effectiveResourceCost) {
				for (ResourceAmount resourceDiscount : this.discountChoice) {
					if (resourceCost.getResourceType() == resourceDiscount.getResourceType()) {
						resourceCost.setAmount(resourceCost.getAmount() - resourceDiscount.getAmount());
					}
				}
			}
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
		if (developmentCard.getCardType() == CardType.CHARACTER && ((DevelopmentCardCharacter) developmentCard).getModifier() != null) {
			this.player.getPlayerHandler().getActiveModifiers().add(((DevelopmentCardCharacter) developmentCard).getModifier());
		}
		gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.cardType).put(this.row, null);
		if (developmentCard.getReward().getActionReward() != null) {
			this.player.getPlayerHandler().setCurrentActionReward(developmentCard.getReward().getActionReward());
			gameHandler.setExpectedAction(developmentCard.getReward().getActionReward().getRequestedAction());
			gameHandler.sendGameUpdateExpectedAction(this.player, developmentCard.getReward().getActionReward().createExpectedAction(gameHandler, this.player));
			return;
		}
		int councilPrivilegesCount = this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
			this.player.getPlayerHandler().getCouncilPrivileges().add(councilPrivilegesCount);
			gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
			gameHandler.sendGameUpdateExpectedAction(this.player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
			return;
		}
		if (gameHandler.getCurrentPhase() == Phase.LEADER) {
			gameHandler.setExpectedAction(null);
			gameHandler.sendGameUpdate(this.player);
			return;
		}
		gameHandler.nextTurn();
	}
}