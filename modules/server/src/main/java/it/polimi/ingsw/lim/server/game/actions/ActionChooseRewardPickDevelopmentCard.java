package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardCharacter;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseRewardPickDevelopmentCard extends ActionInformationsChooseRewardPickDevelopmentCard implements IAction
{
	private final Player player;
	private boolean columnOccupied = false;
	private boolean getBoardPositionReward = true;
	private List<ResourceAmount> effectiveResourceCost;

	public ActionChooseRewardPickDevelopmentCard(int servants, CardType cardType, Row row, Row instantRewardRow, List<ResourceAmount> instantDiscountChoice, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption, Player player)
	{
		super(servants, cardType, row, instantRewardRow, instantDiscountChoice, discountChoice, resourceCostOption);
		this.player = player;
	}

	@Override
	public boolean isLegal()
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player.getConnection());
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
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD) {
			return false;
		}
		// check if the card is already taken
		DevelopmentCard developmentCard = gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
		if (developmentCard == null) {
			return false;
		}
		// check if the player has developmentcard space available
		if (!this.player.getPlayerCardHandler().canAddDevelopmentCard(this.getCardType())) {
			return false;
		}
		if (this.getInstantRewardRow() != Row.THIRD && this.getInstantRewardRow() != Row.FOURTH) {
			return false;
		}
		// check if the board column is occupied
		for (Connection currentPlayer : room.getPlayers()) {
			for (BoardPosition boardPosition : BoardPosition.getDevelopmentCardsColumnPositions(this.getCardType())) {
				if (currentPlayer.getPlayer().isOccupyingBoardPosition(boardPosition)) {
					this.columnOccupied = true;
					break;
				}
			}
			if (this.columnOccupied) {
				break;
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.getServants()) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.applyModifiers(this.player.getActiveModifiers());
		int effectiveServantsValue = eventUseServants.getServants();
		// controllo che la carta contenga il cost option
		if ((this.getResourceCostOption() == null && !developmentCard.getResourceCostOptions().isEmpty()) || (this.getResourceCostOption() != null && !developmentCard.getResourceCostOptions().contains(this.getResourceCostOption()))) {
			return false;
		}
		// check if the player has the requiredResources
		if (this.getResourceCostOption().getRequiredResources() != null) {
			for (ResourceAmount requiredResources : this.getResourceCostOption().getRequiredResources()) {
				int playerResources = this.player.getPlayerResourceHandler().getResources().get(requiredResources.getResourceType());
				if (playerResources < requiredResources.getAmount()) {
					return false;
				}
			}
		}
		// check if the family member and servants value is high enough
		EventPickDevelopmentCard eventPickDevelopmentCard = new EventPickDevelopmentCard(this.player, this.getCardType(), this.getRow(), this.getResourceCostOption() == null ? null : this.getResourceCostOption().getSpentResources(), BoardHandler.getBoardPositionInformations(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow())).getValue() + effectiveServantsValue);
		eventPickDevelopmentCard.applyModifiers(this.player.getActiveModifiers());
		this.effectiveResourceCost = eventPickDevelopmentCard.getResourceCost();
		this.getBoardPositionReward = eventPickDevelopmentCard.isGetBoardPositionReward();
		// if the card is a territory one, check whether the player has enough military points
		if (developmentCard.getCardType() == CardType.TERRITORY && !eventPickDevelopmentCard.isIgnoreTerritoriesSlotLock() && !this.player.getPlayerResourceHandler().isTerritorySlotAvailable(this.player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class).size())) {
			return false;
		}
		// controlla presenza discountchoice nell'array actionreward
		if ((this.getDiscountChoice() == null && !((ActionRewardPickDevelopmentCard) this.player.getCurrentActionReward()).getInstantDiscountChoices().isEmpty()) || (this.getInstantDiscountChoice() != null && !((ActionRewardPickDevelopmentCard) this.player.getCurrentActionReward()).getInstantDiscountChoices().contains(this.getInstantDiscountChoice()))) {
			return false;
		}
		if (this.getResourceCostOption() == null && this.getDiscountChoice() != null) {
			return false;
		}
		if (this.getResourceCostOption() != null) {
			if (this.getDiscountChoice() == null) {
				boolean validDiscountChoice = true;
				for (Modifier modifier : this.player.getActiveModifiers()) {
					if (modifier.getEventClass() == EventPickDevelopmentCard.class && !((ModifierPickDevelopmentCard) modifier).getDiscountChoices().isEmpty()) {
						validDiscountChoice = false;
						break;
					}
				}
				if (!validDiscountChoice) {
					return false;
				}
			} else {
				boolean validDiscountChoice = false;
				for (Modifier modifier : this.player.getActiveModifiers()) {
					if (modifier.getEventClass() == EventPickDevelopmentCard.class && ((ModifierPickDevelopmentCard) modifier).getDiscountChoices().contains(this.getDiscountChoice())) {
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
		if (this.getInstantDiscountChoice() != null) {
			for (ResourceAmount resourceCost : this.effectiveResourceCost) {
				for (ResourceAmount resourceDiscount : this.getInstantDiscountChoice()) {
					if (resourceCost.getResourceType() == resourceDiscount.getResourceType()) {
						resourceCost.setAmount(resourceCost.getAmount() - resourceDiscount.getAmount());
					}
				}
			}
		}
		if (this.getDiscountChoice() != null) {
			for (ResourceAmount resourceCost : this.effectiveResourceCost) {
				for (ResourceAmount resourceDiscount : this.getDiscountChoice()) {
					if (resourceCost.getResourceType() == resourceDiscount.getResourceType()) {
						resourceCost.setAmount(resourceCost.getAmount() - resourceDiscount.getAmount());
					}
				}
			}
		}
		if (this.columnOccupied) {
			if (this.effectiveResourceCost == null) {
				this.effectiveResourceCost = new ArrayList<>();
			}
			this.effectiveResourceCost.add(new ResourceAmount(ResourceType.COIN, 3));
		}
		// prendo prezzo finale e controllo che il giocatore abbia le risorse necessarie
		for (ResourceAmount resourceCost : this.effectiveResourceCost) {
			int playerResources = this.player.getPlayerResourceHandler().getResources().get(resourceCost.getResourceType());
			if (playerResources < resourceCost.getAmount()) {
				return false;
			}
		}
		return eventPickDevelopmentCard.getActionValue() >= BoardHandler.getBoardPositionInformations(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow())).getValue();
	}

	@Override
	public void apply()
	{
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		DevelopmentCard developmentCard = gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
		this.player.getPlayerCardHandler().addDevelopmentCard(developmentCard);
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		this.player.getPlayerResourceHandler().subtractResources(this.effectiveResourceCost);
		List<ResourceAmount> resourceReward = new ArrayList<>(developmentCard.getReward().getResourceAmounts());
		if (this.getBoardPositionReward) {
			resourceReward.addAll(BoardHandler.getBoardPositionInformations(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow())).getResourceAmounts());
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.DEVELOPMENT_CARDS);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		if (developmentCard.getCardType() == CardType.CHARACTER && ((DevelopmentCardCharacter) developmentCard).getModifier() != null) {
			this.player.getActiveModifiers().add(((DevelopmentCardCharacter) developmentCard).getModifier());
		}
		gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).put(this.getRow(), null);
		if (developmentCard.getReward().getActionReward() != null) {
			this.player.setCurrentActionReward(developmentCard.getReward().getActionReward());
			gameHandler.setExpectedAction(developmentCard.getReward().getActionReward().getRequestedAction());
			gameHandler.sendGameUpdateExpectedAction(this.player, developmentCard.getReward().getActionReward().createExpectedAction(gameHandler, this.player));
			return;
		}
		int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			this.player.getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
			this.player.getCouncilPrivileges().add(councilPrivilegesCount);
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