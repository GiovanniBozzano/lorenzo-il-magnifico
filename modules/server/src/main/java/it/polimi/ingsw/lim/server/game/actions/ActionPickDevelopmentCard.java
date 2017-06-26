package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
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
		if (gameHandler.getExpectedAction() != null) {
			return false;
		}
		// check if the family member is usable
		if (this.player.getFamilyMembersPositions().get(this.getFamilyMemberType()) != BoardPosition.NONE) {
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
		// get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.getFamilyMemberType(), BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow()), gameHandler.getFamilyMemberTypeValues().get(this.getFamilyMemberType()));
		eventPlaceFamilyMember.applyModifiers(this.player.getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		// check whether you have another colored family member in the column
		if (this.getFamilyMemberType() != FamilyMemberType.NEUTRAL) {
			for (FamilyMemberType currentFamilyMemberType : this.player.getFamilyMembersPositions().keySet()) {
				if (currentFamilyMemberType != FamilyMemberType.NEUTRAL && BoardPosition.getDevelopmentCardsColumnPositions(this.getCardType()).contains(this.player.getFamilyMembersPositions().get(currentFamilyMemberType))) {
					return false;
				}
			}
		}
		// check if the board column is occupied
		for (Player currentPlayer : gameHandler.getTurnOrder()) {
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
		if (this.getResourceCostOption() != null) {
			for (ResourceAmount requiredResources : this.getResourceCostOption().getRequiredResources()) {
				int playerResources = this.player.getPlayerResourceHandler().getResources().get(requiredResources.getResourceType());
				if (playerResources < requiredResources.getAmount()) {
					return false;
				}
			}
		}
		// check if the family member and servants value is high enough
		EventPickDevelopmentCard eventPickDevelopmentCard = new EventPickDevelopmentCard(this.player, this.getCardType(), this.getRow(), this.getResourceCostOption() == null ? null : this.getResourceCostOption().getSpentResources(), effectiveFamilyMemberValue + effectiveServantsValue);
		eventPickDevelopmentCard.applyModifiers(this.player.getActiveModifiers());
		this.effectiveResourceCost.addAll(eventPickDevelopmentCard.getResourceCost());
		this.getBoardPositionReward = eventPickDevelopmentCard.isGetBoardPositionReward();
		// if the card is a territory one, check whether the player has enough military points
		if (developmentCard.getCardType() == CardType.TERRITORY && !eventPickDevelopmentCard.isIgnoreTerritoriesSlotLock() && !this.player.getPlayerResourceHandler().isTerritorySlotAvailable(this.player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class).size())) {
			return false;
		}
		if (this.getResourceCostOption() == null && !this.getDiscountChoice().isEmpty()) {
			return false;
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
					return false;
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
					return false;
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
		gameHandler.setCurrentPhase(Phase.FAMILY_MEMBER);
		this.player.getFamilyMembersPositions().put(this.getFamilyMemberType(), BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow()));
		DevelopmentCard developmentCard = gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(this.getCardType()).get(this.getRow());
		this.player.getPlayerCardHandler().addDevelopmentCard(developmentCard);
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		this.player.getPlayerResourceHandler().subtractResources(this.effectiveResourceCost);
		List<ResourceAmount> resourceReward = new ArrayList<>(developmentCard.getReward().getResourceAmounts());
		BoardPosition boardPosition = BoardPosition.getDevelopmentCardPosition(this.getCardType(), this.getRow());
		if (this.getBoardPositionReward) {
			resourceReward.addAll(BoardHandler.getBoardPositionInformations(boardPosition).getResourceAmounts());
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.DEVELOPMENT_CARDS);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		if (developmentCard.getCardType() == CardType.CHARACTER && ((DevelopmentCardCharacter) developmentCard).getModifier() != null) {
			this.player.getActiveModifiers().add(((DevelopmentCardCharacter) developmentCard).getModifier());
		}
		this.player.getFamilyMembersPositions().put(this.getFamilyMemberType(), boardPosition);
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
		gameHandler.nextTurn();
	}
}
