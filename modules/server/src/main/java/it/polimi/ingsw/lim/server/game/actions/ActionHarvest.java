package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationHarvest;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.enums.WorkSlotType;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventHarvest;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActionHarvest extends ActionInformationHarvest implements IAction
{
	private final transient Player player;
	private transient WorkSlotType workSlotType;
	private transient int effectiveActionValue;

	public ActionHarvest(FamilyMemberType familyMemberType, int servants, Player player)
	{
		super(familyMemberType, servants);
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
		// check if the board slot is occupied and get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.getFamilyMemberType(), BoardPosition.HARVEST_SMALL, this.player.getRoom().getGameHandler().getFamilyMemberTypeValues().get(this.getFamilyMemberType()));
		eventPlaceFamilyMember.fire();
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (!eventPlaceFamilyMember.isIgnoreOccupied()) {
			for (Player currentPlayer : this.player.getRoom().getGameHandler().getTurnOrder()) {
				if (currentPlayer.isOccupyingBoardPosition(BoardPosition.HARVEST_SMALL)) {
					if (this.player.getRoom().getGameHandler().getRoom().getPlayers().size() < 3) {
						throw new GameActionFailedException("This action cannot be performed because the slot has already been occupied");
					} else {
						this.workSlotType = WorkSlotType.BIG;
						break;
					}
				}
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.getServants()) {
			throw new GameActionFailedException("You do not have the amount of servants you want to use");
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.fire();
		int effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		EventHarvest eventHarvest = new EventHarvest(this.player, effectiveFamilyMemberValue + effectiveServants);
		eventHarvest.fire();
		this.effectiveActionValue = eventHarvest.getActionValue();
		if (this.effectiveActionValue < (this.workSlotType == WorkSlotType.BIG ? BoardHandler.getBoardPositionInformation(BoardPosition.HARVEST_BIG).getValue() : BoardHandler.getBoardPositionInformation(BoardPosition.HARVEST_SMALL).getValue())) {
			throw new GameActionFailedException("The value of the selected Family Member is not enough to perform this action");
		}
	}

	@Override
	public void apply()
	{
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.FAMILY_MEMBER);
		this.player.getFamilyMembersPositions().put(this.getFamilyMemberType(), this.workSlotType == WorkSlotType.BIG ? BoardPosition.HARVEST_BIG : BoardPosition.HARVEST_SMALL);
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		List<ResourceAmount> resourceReward = new ArrayList<>();
		for (DevelopmentCardTerritory developmentCardTerritory : this.player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class)) {
			if (developmentCardTerritory.getActivationValue() <= this.effectiveActionValue) {
				resourceReward.addAll(developmentCardTerritory.getHarvestResources());
			}
		}
		if (this.effectiveActionValue >= this.player.getPersonalBonusTile().getHarvestActivationCost()) {
			resourceReward.addAll(this.player.getPersonalBonusTile().getHarvestInstantResources());
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, resourceReward, ResourcesSource.WORK);
		eventGainResources.fire();
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " harvested with his " + this.getFamilyMemberType().name().toLowerCase() + " family member");
		if (Utils.sendCouncilPrivileges(this.player)) {
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
