package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardCharacter;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

import java.util.ArrayList;
import java.util.List;

public class ActionPickDevelopmentCard extends ActionInformationsPickDevelopmentCard implements IAction
{
	private transient final Player player;
	private transient boolean columnOccupied = false;
	private transient boolean getBoardPositionReward = true;
	private transient final List<ResourceAmount> effectiveResourceCost = new ArrayList<>();

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
			throw new GameActionFailedException("");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != null) {
			throw new GameActionFailedException("");
		}
		// check if the family member is usable
		if (this.player.getFamilyMembersPositions().get(this.getFamilyMemberType()) != BoardPosition.NONE) {
			throw new GameActionFailedException("");
		}
		// check if the card is already taken
		DevelopmentCard developmentCard = this.player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
		if (developmentCard == null) {
			throw new GameActionFailedException("");
		}
		// check if the player has developmentcard space available
		if (!this.player.getPlayerCardHandler().canAddDevelopmentCard(this.getCardType())) {
			throw new GameActionFailedException("");
		}
		// get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.getFamilyMemberType(), BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow()), this.player.getRoom().getGameHandler().getFamilyMemberTypeValues().get(this.getFamilyMemberType()));
		eventPlaceFamilyMember.applyModifiers(this.player.getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		// check whether you have another colored family member in the column
		if (this.getFamilyMemberType() != FamilyMemberType.NEUTRAL) {
			for (FamilyMemberType currentFamilyMemberType : this.player.getFamilyMembersPositions().keySet()) {
				if (currentFamilyMemberType != FamilyMemberType.NEUTRAL && BoardPosition.getDevelopmentCardsColumnPositions(this.getCardType()).contains(this.player.getFamilyMembersPositions().get(currentFamilyMemberType))) {
					throw new GameActionFailedException("");
				}
			}
		}
		// check if the board column is occupied
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
		EventPickDevelopmentCard eventPickDevelopmentCard = new EventPickDevelopmentCard(this.player, this.getCardType(), this.getRow(), this.getResourceCostOption() == null ? null : this.getResourceCostOption().getSpentResources(), effectiveFamilyMemberValue + effectiveServantsValue);
		eventPickDevelopmentCard.applyModifiers(this.player.getActiveModifiers());
		this.effectiveResourceCost.addAll(eventPickDevelopmentCard.getResourceCost());
		this.getBoardPositionReward = eventPickDevelopmentCard.isGetBoardPositionReward();
		// if the card is a territory one, check whether the player has enough military points
		if (developmentCard.getCardType() == CardType.TERRITORY && !eventPickDevelopmentCard.isIgnoreTerritoriesSlotLock() && !this.player.isTerritorySlotAvailable(this.player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class).size())) {
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
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.FAMILY_MEMBER);
		this.player.getFamilyMembersPositions().put(this.getFamilyMemberType(), BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow()));
		DevelopmentCard developmentCard = this.player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
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
		this.player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).put(this.getRow(), null);
		if (developmentCard.getReward().getActionReward() != null && developmentCard.getReward().getActionReward().getRequestedAction() != null) {
			this.player.setCurrentActionReward(developmentCard.getReward().getActionReward());
			this.player.getRoom().getGameHandler().setExpectedAction(developmentCard.getReward().getActionReward().getRequestedAction());
			this.player.getRoom().getGameHandler().sendGameUpdateExpectedAction(this.player, developmentCard.getReward().getActionReward().createExpectedAction(this.player.getRoom().getGameHandler(), this.player));
			return;
		}
		int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			this.player.getRoom().getGameHandler().setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
			this.player.getRoom().getGameHandler().sendGameUpdateExpectedAction(this.player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
