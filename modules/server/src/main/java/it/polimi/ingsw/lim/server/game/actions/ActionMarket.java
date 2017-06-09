package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.MarketSlot;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.EnumMap;
import java.util.Map;

public class ActionMarket implements IAction
{
	private static final Map<MarketSlot, BoardPosition> MARKET_BOARD_POSITIONS = new EnumMap<>(MarketSlot.class);
	private int effectiveServants;

	static {
		ActionMarket.MARKET_BOARD_POSITIONS.put(MarketSlot.FIRST, BoardPosition.MARKET_1);
		ActionMarket.MARKET_BOARD_POSITIONS.put(MarketSlot.SECOND, BoardPosition.MARKET_2);
		ActionMarket.MARKET_BOARD_POSITIONS.put(MarketSlot.THIRD, BoardPosition.MARKET_3);
		ActionMarket.MARKET_BOARD_POSITIONS.put(MarketSlot.FOURTH, BoardPosition.MARKET_4);
	}

	private final Connection player;
	private final FamilyMemberType familyMemberType;
	private final MarketSlot marketSlot;

	public ActionMarket(Connection player, FamilyMemberType familyMemberType, MarketSlot marketSlot)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
		this.marketSlot = marketSlot;
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
		if (room.getGameHandler().getExpectedAction() != null) {
			return false;
		}
		// check if the board slot is occupied and get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.familyMemberType, ActionMarket.MARKET_BOARD_POSITIONS.get(this.marketSlot), gameHandler.getFamilyMemberTypeValues().get(this.familyMemberType));
		eventPlaceFamilyMember.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (!eventPlaceFamilyMember.isIgnoreOccupied()) {
			for (Connection currentPlayer : room.getPlayers()) {
				if (currentPlayer.getPlayerInformations().isOccupyingBoardPosition(ActionMarket.MARKET_BOARD_POSITIONS.get(this.marketSlot))) {
					return false;
				}
			}
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.effectiveServants);
		eventUseServants.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		this.effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		return effectiveFamilyMemberValue + this.effectiveServants >= BoardHandler.getBoardPositionInformations(ActionMarket.MARKET_BOARD_POSITIONS.get(this.marketSlot)).getValue();
	}

	@Override
	public void apply()
	{
	}
}
