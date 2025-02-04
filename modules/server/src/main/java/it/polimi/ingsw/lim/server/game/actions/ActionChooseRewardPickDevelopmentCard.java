package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardCharacter;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseRewardPickDevelopmentCard extends ActionInformationChooseRewardPickDevelopmentCard implements IAction
{
	private final transient Player player;
	private final transient List<ResourceAmount> effectiveResourceCost = new ArrayList<>();
	private transient boolean columnOccupied = false;
	private transient boolean getBoardPositionReward = true;

	public ActionChooseRewardPickDevelopmentCard(int servants, CardType cardType, Row row, Row instantRewardRow, List<ResourceAmount> instantDiscountChoice, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption, Player player)
	{
		super(servants, cardType, row, instantRewardRow, instantDiscountChoice, discountChoice, resourceCostOption == null ? null : new ResourceCostOption(resourceCostOption));
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if it is the player's turn
		if (this.player != this.player.getRoom().getGameHandler().getTurnPlayer()) {
			throw new GameActionFailedException("It's not your turn");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check if the card has been already taken
		DevelopmentCard developmentCard = this.player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
		if (developmentCard == null) {
			throw new GameActionFailedException("This Development Card has already been taken");
		}
		// check if the player has developmentcard space available
		if (!this.player.getPlayerCardHandler().canAddDevelopmentCard(this.getCardType())) {
			throw new GameActionFailedException("You don't have enough space available on your board to get another card of the current type");
		}
		if (this.getInstantRewardRow() != Row.THIRD && this.getInstantRewardRow() != Row.FOURTH) {
			throw new GameActionFailedException("Please choose the correct reward");
		}
		// check if the board column is occupied
		for (Connection currentPlayer : this.player.getRoom().getGameHandler().getRoom().getPlayers()) {
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
			throw new GameActionFailedException("You don't have the amount of servants you want to use");
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.fire();
		int effectiveServantsValue = eventUseServants.getServants();
		// check if the card contains cost option array
		if ((this.getResourceCostOption() == null && !developmentCard.getResourceCostOptions().isEmpty()) || (this.getResourceCostOption() != null && !developmentCard.getResourceCostOptions().contains(this.getResourceCostOption()))) {
			throw new GameActionFailedException("This cost option is not present in the current card");
		}
		// check if the player has the requiredResources
		if (this.getResourceCostOption() != null && !this.player.getPlayerResourceHandler().canAffordResources(this.getResourceCostOption().getRequiredResources())) {
			throw new GameActionFailedException("You don't have the required resources to perform this action");
		}
		// check if the family member and servants value is high enough
		EventPickDevelopmentCard eventPickDevelopmentCard = new EventPickDevelopmentCard(this.player, this.getCardType(), this.getRow(), this.getResourceCostOption() == null ? null : this.getResourceCostOption().getSpentResources(), ((ActionRewardPickDevelopmentCard) this.player.getCurrentActionReward()).getValue() + effectiveServantsValue);
		eventPickDevelopmentCard.fire();
		this.effectiveResourceCost.addAll(eventPickDevelopmentCard.getResourceCost());
		this.getBoardPositionReward = eventPickDevelopmentCard.isGetBoardPositionReward();
		// if the card is a territory one, check whether the player has enough military points
		if (developmentCard.getCardType() == CardType.TERRITORY && !eventPickDevelopmentCard.isIgnoreTerritoriesSlotLock() && !this.player.isTerritorySlotAvailable(this.player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class).size())) {
			throw new GameActionFailedException("You don't have enough military points to unlock the slot necessary to perform this action");
		}
		Utils.checkValidDiscount(this.player, this.getCardType(), this.getInstantDiscountChoice(), this.getDiscountChoice(), this.getResourceCostOption());
		// check the presence of discountchoice in the actionreward array
		Utils.subtractDiscount(this.effectiveResourceCost, this.getInstantDiscountChoice());
		Utils.subtractDiscount(this.effectiveResourceCost, this.getDiscountChoice());
		if (this.columnOccupied) {
			if (this.player.getRoom().getRoomType() == RoomType.EXTENDED) {
				this.effectiveResourceCost.add(new ResourceAmount(ResourceType.COIN, 5));
			} else {
				this.effectiveResourceCost.add(new ResourceAmount(ResourceType.COIN, 3));
			}
		}
		// check if the player has enough Resources
		if (!this.player.getPlayerResourceHandler().canAffordResources(this.effectiveResourceCost)) {
			throw new GameActionFailedException("You don't have the necessary resources to perform this action");
		}
		if (eventPickDevelopmentCard.getActionValue() < BoardHandler.getBoardPositionInformation(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow())).getValue()) {
			throw new GameActionFailedException("The action value is too low to get this card");
		}
	}

	@Override
	public void apply()
	{
		DevelopmentCard developmentCard = this.player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
		this.player.getPlayerCardHandler().addDevelopmentCard(developmentCard);
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		this.player.getPlayerResourceHandler().subtractResources(this.effectiveResourceCost);
		List<ResourceAmount> resourceReward = new ArrayList<>(developmentCard.getReward().getResourceAmounts());
		if (this.getBoardPositionReward) {
			resourceReward.addAll(BoardHandler.getBoardPositionInformation(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getInstantRewardRow())).getResourceAmounts());
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.DEVELOPMENT_CARDS);
		eventGainResources.fire();
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		if (developmentCard.getCardType() == CardType.CHARACTER && ((DevelopmentCardCharacter) developmentCard).getModifier() != null) {
			this.player.getActiveModifiers().add(((DevelopmentCardCharacter) developmentCard).getModifier());
		}
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " picked " + CommonUtils.getCardTypesNames().get(developmentCard.getCardType()).toLowerCase() + " card " + developmentCard.getDisplayName());
		this.player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).put(this.getRow(), null);
		if (Utils.sendActionReward(this.player, developmentCard.getReward().getActionReward())) {
			return;
		}
		if (Utils.sendCouncilPrivileges(this.player)) {
			return;
		}
		if (Utils.checkLeaderPhase(this.player)) {
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}