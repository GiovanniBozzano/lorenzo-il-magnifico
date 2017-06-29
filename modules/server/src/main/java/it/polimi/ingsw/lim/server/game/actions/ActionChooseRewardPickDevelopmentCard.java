package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
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
	private transient final Player player;
	private transient boolean columnOccupied = false;
	private transient boolean getBoardPositionReward = true;
	private transient List<ResourceAmount> effectiveResourceCost;

	public ActionChooseRewardPickDevelopmentCard(int servants, CardType cardType, Row row, Row instantRewardRow, List<ResourceAmount> instantDiscountChoice, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption, Player player)
	{
		super(servants, cardType, row, instantRewardRow, instantDiscountChoice, discountChoice, resourceCostOption);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			throw new GameActionFailedException("");
		}
		// check if the game has started
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			throw new GameActionFailedException("");
		}
		// check if it is the player's turn
		if (this.player != gameHandler.getTurnPlayer()) {
			throw new GameActionFailedException("");
		}
		// check whether the server expects the player to make this action
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD) {
			throw new GameActionFailedException("");
		}
		// check if the card is already taken
		DevelopmentCard developmentCard = gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
		if (developmentCard == null) {
			throw new GameActionFailedException("");
		}
		// check if the player has developmentcard space available
		if (!this.player.getPlayerCardHandler().canAddDevelopmentCard(this.getCardType())) {
			throw new GameActionFailedException("");
		}
		if (this.getInstantRewardRow() != Row.THIRD && this.getInstantRewardRow() != Row.FOURTH) {
			throw new GameActionFailedException("");
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
			throw new GameActionFailedException("");
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.applyModifiers(this.player.getActiveModifiers());
		int effectiveServantsValue = eventUseServants.getServants();
		// controllo che la carta contenga il cost option
		if ((this.getResourceCostOption() == null && !developmentCard.getResourceCostOptions().isEmpty()) || (this.getResourceCostOption() != null && !developmentCard.getResourceCostOptions().contains(this.getResourceCostOption()))) {
			throw new GameActionFailedException("");
		}
		// check if the player has the requiredResources
		if (this.getResourceCostOption() != null) {
			for (ResourceAmount requiredResources : this.getResourceCostOption().getRequiredResources()) {
				int playerResources = this.player.getPlayerResourceHandler().getResources().get(requiredResources.getResourceType());
				if (playerResources < requiredResources.getAmount()) {
					throw new GameActionFailedException("");
				}
			}
		}
		// check if the family member and servants value is high enough
		EventPickDevelopmentCard eventPickDevelopmentCard = new EventPickDevelopmentCard(this.player, this.getCardType(), this.getRow(), this.getResourceCostOption() == null ? null : this.getResourceCostOption().getSpentResources(), BoardHandler.getBoardPositionInformations(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow())).getValue() + effectiveServantsValue);
		eventPickDevelopmentCard.applyModifiers(this.player.getActiveModifiers());
		this.effectiveResourceCost = eventPickDevelopmentCard.getResourceCost();
		this.getBoardPositionReward = eventPickDevelopmentCard.isGetBoardPositionReward();
		// if the card is a territory one, check whether the player has enough military points
		if (developmentCard.getCardType() == CardType.TERRITORY && !eventPickDevelopmentCard.isIgnoreTerritoriesSlotLock() && !this.player.isTerritorySlotAvailable(this.player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class).size())) {
			throw new GameActionFailedException("");
		}
		// controlla presenza discountchoice nell'array actionreward
		if ((this.getInstantDiscountChoice().isEmpty() && !((ActionRewardPickDevelopmentCard) this.player.getCurrentActionReward()).getInstantDiscountChoices().isEmpty()) || (!this.getInstantDiscountChoice().isEmpty() && !((ActionRewardPickDevelopmentCard) this.player.getCurrentActionReward()).getInstantDiscountChoices().contains(this.getInstantDiscountChoice()))) {
			throw new GameActionFailedException("");
		}
		if (this.getResourceCostOption() == null && !this.getDiscountChoice().isEmpty()) {
			throw new GameActionFailedException("");
		}
		if (this.getResourceCostOption() != null) {
			if (this.getDiscountChoice().isEmpty()) {
				boolean validDiscountChoice = true;
				for (Modifier modifier : this.player.getActiveModifiers()) {
					if (modifier instanceof ModifierPickDevelopmentCard && ((ModifierPickDevelopmentCard) modifier).getCardType() == this.getCardType() && (((ModifierPickDevelopmentCard) modifier).getDiscountChoices() != null || !((ModifierPickDevelopmentCard) modifier).getDiscountChoices().isEmpty())) {
						validDiscountChoice = false;
						break;
					}
				}
				if (!validDiscountChoice) {
					throw new GameActionFailedException("");
				}
			} else {
				boolean validDiscountChoice = false;
				for (Modifier modifier : this.player.getActiveModifiers()) {
					if (modifier instanceof ModifierPickDevelopmentCard && ((ModifierPickDevelopmentCard) modifier).getCardType() == this.getCardType() && ((ModifierPickDevelopmentCard) modifier).getDiscountChoices() != null && ((ModifierPickDevelopmentCard) modifier).getDiscountChoices().contains(this.getDiscountChoice())) {
						validDiscountChoice = true;
						break;
					}
				}
				if (!validDiscountChoice) {
					throw new GameActionFailedException("");
				}
			}
		}
		// getcardprice e togli discountchoice
		if (!this.getInstantDiscountChoice().isEmpty()) {
			for (ResourceAmount effectiveResourceAmount : this.effectiveResourceCost) {
				for (ResourceAmount discountResourceAmount : this.getInstantDiscountChoice()) {
					if (discountResourceAmount.getResourceType() == effectiveResourceAmount.getResourceType()) {
						effectiveResourceAmount.setAmount(effectiveResourceAmount.getAmount() - discountResourceAmount.getAmount());
					}
				}
			}
		}
		if (!this.getDiscountChoice().isEmpty()) {
			for (ResourceAmount effectiveResourceAmount : this.effectiveResourceCost) {
				for (ResourceAmount discountResourceAmount : this.getDiscountChoice()) {
					if (discountResourceAmount.getResourceType() == effectiveResourceAmount.getResourceType()) {
						effectiveResourceAmount.setAmount(effectiveResourceAmount.getAmount() - discountResourceAmount.getAmount());
					}
				}
			}
		}
		if (this.columnOccupied) {
			this.effectiveResourceCost.add(new ResourceAmount(ResourceType.COIN, 3));
		}
		// prendo prezzo finale e controllo che il giocatore abbia le risorse necessarie
		for (ResourceAmount resourceCost : this.effectiveResourceCost) {
			int playerResources = this.player.getPlayerResourceHandler().getResources().get(resourceCost.getResourceType());
			if (playerResources < resourceCost.getAmount()) {
				throw new GameActionFailedException("");
			}
		}
		if (eventPickDevelopmentCard.getActionValue() < BoardHandler.getBoardPositionInformations(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow())).getValue()) {
			throw new GameActionFailedException("");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			throw new GameActionFailedException("");
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			throw new GameActionFailedException("");
		}
		DevelopmentCard developmentCard = gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
		this.player.getPlayerCardHandler().addDevelopmentCard(developmentCard);
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		this.player.getPlayerResourceHandler().subtractResources(this.effectiveResourceCost);
		List<ResourceAmount> resourceReward = new ArrayList<>(developmentCard.getReward().getResourceAmounts());
		if (this.getBoardPositionReward) {
			resourceReward.addAll(BoardHandler.getBoardPositionInformations(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getInstantRewardRow())).getResourceAmounts());
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.DEVELOPMENT_CARDS);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		if (developmentCard.getCardType() == CardType.CHARACTER && ((DevelopmentCardCharacter) developmentCard).getModifier() != null) {
			this.player.getActiveModifiers().add(((DevelopmentCardCharacter) developmentCard).getModifier());
		}
		gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).put(this.getRow(), null);
		if (developmentCard.getReward().getActionReward() != null && developmentCard.getReward().getActionReward().getRequestedAction() != null) {
			this.player.setCurrentActionReward(developmentCard.getReward().getActionReward());
			gameHandler.setExpectedAction(developmentCard.getReward().getActionReward().getRequestedAction());
			gameHandler.sendGameUpdateExpectedAction(this.player, developmentCard.getReward().getActionReward().createExpectedAction(gameHandler, this.player));
			return;
		}
		int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
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