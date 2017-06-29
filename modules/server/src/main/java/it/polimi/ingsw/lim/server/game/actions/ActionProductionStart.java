package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsProductionStart;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionProductionTrade;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.enums.WorkSlotType;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventProductionStart;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

import java.util.ArrayList;
import java.util.List;

public class ActionProductionStart extends ActionInformationsProductionStart implements IAction
{
	private transient final Player player;
	private transient WorkSlotType workSlotType;
	private transient int effectiveActionValue;

	public ActionProductionStart(FamilyMemberType familyMemberType, int servants, Player player)
	{
		super(familyMemberType, servants);
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
		// check if the board slot is occupied and get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.getFamilyMemberType(), BoardPosition.PRODUCTION_SMALL, gameHandler.getFamilyMemberTypeValues().get(this.getFamilyMemberType()));
		eventPlaceFamilyMember.applyModifiers(this.player.getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (!eventPlaceFamilyMember.isIgnoreOccupied()) {
			for (Player currentPlayer : gameHandler.getTurnOrder()) {
				if (currentPlayer.isOccupyingBoardPosition(BoardPosition.PRODUCTION_SMALL)) {
					if (room.getPlayers().size() < 3) {
						return false;
					} else {
						this.workSlotType = WorkSlotType.BIG;
						break;
					}
				}
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.getServants()) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.applyModifiers(this.player.getActiveModifiers());
		int effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		EventProductionStart eventProductionStart = new EventProductionStart(this.player, effectiveFamilyMemberValue + effectiveServants);
		eventProductionStart.applyModifiers(this.player.getActiveModifiers());
		this.effectiveActionValue = eventProductionStart.getActionValue();
		return this.effectiveActionValue >= (this.workSlotType == WorkSlotType.BIG ? BoardHandler.getBoardPositionInformations(BoardPosition.PRODUCTION_BIG).getValue() : BoardHandler.getBoardPositionInformations(BoardPosition.PRODUCTION_SMALL).getValue());
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
		this.player.getFamilyMembersPositions().put(this.getFamilyMemberType(), this.workSlotType == WorkSlotType.BIG ? BoardPosition.PRODUCTION_BIG : BoardPosition.PRODUCTION_SMALL);
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		EventGainResources eventGainResources = new EventGainResources(this.player, this.player.getPersonalBonusTile().getProductionInstantResources(), ResourcesSource.WORK);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		this.player.setCurrentProductionValue(this.effectiveActionValue);
		gameHandler.setExpectedAction(ActionType.PRODUCTION_TRADE);
		List<Integer> availableCards = new ArrayList<>();
		for (DevelopmentCardBuilding developmentCardBuilding : this.player.getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
			if (developmentCardBuilding.getActivationValue() <= this.effectiveActionValue) {
				availableCards.add(developmentCardBuilding.getIndex());
			}
		}
		if (availableCards.isEmpty()) {
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
		gameHandler.sendGameUpdateExpectedAction(this.player, new ExpectedActionProductionTrade(availableCards));
	}
}
