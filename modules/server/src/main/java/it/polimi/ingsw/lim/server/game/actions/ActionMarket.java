package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.MarketSlot;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsMarket;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

public class ActionMarket extends ActionInformationsMarket implements IAction
{
	private final transient Player player;

	public ActionMarket(FamilyMemberType familyMemberType, int servants, MarketSlot marketSlot, Player player)
	{
		super(familyMemberType, servants, marketSlot);
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
		if (this.player.getRoom().getGameHandler().getExpectedAction() != null) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check market slots' presence
		if (((this.getMarketSlot() == MarketSlot.SIXTH || this.getMarketSlot() == MarketSlot.FIFTH) && this.player.getRoom().getGameHandler().getRoom().getPlayers().size() < 5) || ((this.getMarketSlot() == MarketSlot.FOURTH || this.getMarketSlot() == MarketSlot.THIRD) && this.player.getRoom().getGameHandler().getRoom().getPlayers().size() < 4)) {
			throw new GameActionFailedException("This market's slot is not available because there are not enough players in the game");
		}
		// check if the family member is usable
		if (this.player.getFamilyMembersPositions().get(this.getFamilyMemberType()) != BoardPosition.NONE) {
			throw new GameActionFailedException("Selected Family Member has already been used");
		}
		// check if the board slot is occupied and get effective family member value
		BoardPosition boardPosition = BoardPosition.getMarketPositions().get(this.getMarketSlot());
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.getFamilyMemberType(), boardPosition, this.player.getRoom().getGameHandler().getFamilyMemberTypeValues().get(this.getFamilyMemberType()));
		eventPlaceFamilyMember.applyModifiers(this.player.getActiveModifiers());
		if (eventPlaceFamilyMember.isCancelled()) {
			throw new GameActionFailedException("You cannot place Family Member in this slot due to a Malus");
		}
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (!eventPlaceFamilyMember.isIgnoreOccupied()) {
			for (Player currentPlayer : this.player.getRoom().getGameHandler().getTurnOrder()) {
				if (currentPlayer.isOccupyingBoardPosition(boardPosition)) {
					throw new GameActionFailedException("This action cannot be performed because the slot has already been occupied");
				}
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.getServants()) {
			throw new GameActionFailedException("You don't have the amount of servants you want to use");
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.applyModifiers(this.player.getActiveModifiers());
		int effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		if (effectiveFamilyMemberValue + effectiveServants < BoardHandler.getBoardPositionInformations(boardPosition).getValue()) {
			throw new GameActionFailedException("The value of the selected Family Member is not enough to perform this action");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.FAMILY_MEMBER);
		this.player.getFamilyMembersPositions().put(this.getFamilyMemberType(), BoardPosition.getMarketPositions().get(this.getMarketSlot()));
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		EventGainResources eventGainResources = new EventGainResources(this.player, BoardHandler.getBoardPositionInformations(BoardPosition.getMarketPositions().get(this.getMarketSlot())).getResourceAmounts(), ResourcesSource.MARKET);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " started a business in the market with his " + this.getFamilyMemberType().name().toLowerCase() + " family member");
		if (Utils.sendCouncilPrivileges(this.player)) {
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
