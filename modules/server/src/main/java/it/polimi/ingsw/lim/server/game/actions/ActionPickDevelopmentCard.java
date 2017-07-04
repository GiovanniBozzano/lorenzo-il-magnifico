package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardCharacter;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActionPickDevelopmentCard extends ActionInformationPickDevelopmentCard implements IAction
{
	private final transient Player player;
	private transient boolean columnOccupied = false;
	private transient boolean getBoardPositionReward = true;
	private final transient List<ResourceAmount> effectiveResourceCost = new ArrayList<>();

	public ActionPickDevelopmentCard(FamilyMemberType familyMemberType, int servants, CardType cardType, Row row, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption, Player player)
	{
		super(familyMemberType, servants, cardType, row, discountChoice, resourceCostOption);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if it is the player's turn
		if (this.player != this.player.getRoom().getGameHandler().getTurnPlayer()) {
			throw new GameActionFailedException("It is not your turn");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != null) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check if the family member is usable
		if (this.player.getFamilyMembersPositions().get(this.getFamilyMemberType()) != BoardPosition.NONE) {
			throw new GameActionFailedException("Selected Family Member has already been used");
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
		// get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.getFamilyMemberType(), BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow()), this.player.getRoom().getGameHandler().getFamilyMemberTypeValues().get(this.getFamilyMemberType()));
		eventPlaceFamilyMember.applyModifiers(this.player.getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		// check whether you have another colored family member in the column
		if (this.getFamilyMemberType() != FamilyMemberType.NEUTRAL) {
			for (FamilyMemberType currentFamilyMemberType : this.player.getFamilyMembersPositions().keySet()) {
				if (currentFamilyMemberType != FamilyMemberType.NEUTRAL && BoardPosition.getDevelopmentCardsColumnPositions(this.getCardType()).contains(this.player.getFamilyMembersPositions().get(currentFamilyMemberType))) {
					throw new GameActionFailedException("You cannot perform this action because you already have a Family Member on this Tower");
				}
			}
		}
		// check if the board column is occupied
		if (!eventPlaceFamilyMember.isIgnoreOccupiedTax()) {
			for (Player currentPlayer : this.player.getRoom().getGameHandler().getTurnOrder()) {
				for (BoardPosition boardPosition : BoardPosition.getDevelopmentCardsColumnPositions(this.getCardType())) {
					if (currentPlayer.isOccupyingBoardPosition(boardPosition)) {
						this.columnOccupied = true;
						break;
					}
				}
				if (this.columnOccupied) {
					break;
				}
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.getServants()) {
			throw new GameActionFailedException("You do not have the number of servants you want to use");
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.applyModifiers(this.player.getActiveModifiers());
		int effectiveServantsValue = eventUseServants.getServants();
		// check if the card contains cost option array
		if ((this.getResourceCostOption() == null && !developmentCard.getResourceCostOptions().isEmpty()) || (this.getResourceCostOption() != null && !developmentCard.getResourceCostOptions().contains(this.getResourceCostOption()))) {
			throw new GameActionFailedException("This cost option is not present in the current card");
		}
		// check if the player has the requiredResources
		if (this.getResourceCostOption() != null && !this.player.getPlayerResourceHandler().canAffordResources(this.getResourceCostOption().getRequiredResources())) {
			throw new GameActionFailedException("You do not have the necessary resources to perform this action");
		}
		// check if the family member and servants value is high enough
		EventPickDevelopmentCard eventPickDevelopmentCard = new EventPickDevelopmentCard(this.player, this.getCardType(), this.getRow(), this.getResourceCostOption() == null ? null : this.getResourceCostOption().getSpentResources(), effectiveFamilyMemberValue + effectiveServantsValue);
		eventPickDevelopmentCard.applyModifiers(this.player.getActiveModifiers());
		this.effectiveResourceCost.addAll(eventPickDevelopmentCard.getResourceCost());
		this.getBoardPositionReward = eventPickDevelopmentCard.isGetBoardPositionReward();
		// if the card is a territory one, check whether the player has enough military points
		if (developmentCard.getCardType() == CardType.TERRITORY && !eventPickDevelopmentCard.isIgnoreTerritoriesSlotLock() && !this.player.isTerritorySlotAvailable(this.player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class).size())) {
			throw new GameActionFailedException("You do not have enough military points to unlock the slot necessary to perform this action");
		}
		Utils.checkValidDiscount(this.player, this.getCardType(), this.getDiscountChoice(), this.getResourceCostOption());
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
		// check if the player has enough Resources
		if (!this.player.getPlayerResourceHandler().canAffordResources(this.effectiveResourceCost)) {
			throw new GameActionFailedException("You do not have enough resources to get the card");
		}
		// check the ActionValue
		if (eventPickDevelopmentCard.getActionValue() < BoardHandler.getBoardPositionInformation(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow())).getValue()) {
			throw new GameActionFailedException("The action value is too low to get this card");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.FAMILY_MEMBER);
		this.player.getFamilyMembersPositions().put(this.getFamilyMemberType(), BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow()));
		DevelopmentCard developmentCard = this.player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
		this.player.getPlayerCardHandler().addDevelopmentCard(developmentCard);
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		this.player.getPlayerResourceHandler().subtractResources(this.effectiveResourceCost);
		List<ResourceAmount> resourceReward = new ArrayList<>(developmentCard.getReward().getResourceAmounts());
		if (this.getBoardPositionReward) {
			resourceReward.addAll(BoardHandler.getBoardPositionInformation(BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow())).getResourceAmounts());
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.DEVELOPMENT_CARDS);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		if (developmentCard.getCardType() == CardType.CHARACTER && ((DevelopmentCardCharacter) developmentCard).getModifier() != null) {
			this.player.getActiveModifiers().add(((DevelopmentCardCharacter) developmentCard).getModifier());
		}
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " picked " + CommonUtils.getCardTypesNames().get(developmentCard.getCardType()).toLowerCase() + " card " + developmentCard.getDisplayName() + " with his " + this.getFamilyMemberType().name().toLowerCase() + " family member");
		this.player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).put(this.getRow(), null);
		if (Utils.sendActionReward(this.player, developmentCard.getReward().getActionReward())) {
			return;
		}
		if (Utils.sendCouncilPrivileges(this.player)) {
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
