package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionCouncilPalace implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;
	private int servants;

	public ActionCouncilPalace(Connection player, FamilyMemberType familyMemberType, int servants)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
		this.servants = servants;
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
		if (gameHandler.getExpectedAction() != ActionType.COUNCIL_PALACE) {
			return false;
		}
		// get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.familyMemberType, BoardPosition.COUNCIL_PALACE, gameHandler.getFamilyMemberTypeValues().get(this.familyMemberType));
		eventPlaceFamilyMember.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		// check if the player has the servants he sent
		if (this.player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.servants) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.servants);
		eventUseServants.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		int effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		return effectiveFamilyMemberValue + effectiveServants >= BoardHandler.getBoardPositionInformations(BoardPosition.COUNCIL_PALACE).getValue();
	}

	@Override
	public void apply()
	{
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		gameHandler.getBoardHandler().getCouncilPalaceOrder().add(this.player);
		EventGainResources eventGainResources = new EventGainResources(this.player, BoardHandler.getBoardPositionInformations(BoardPosition.COUNCIL_PALACE).getResourceAmounts(), ResourcesSource.COUNCIL_PALACE);
		eventGainResources.applyModifiers(this.player.getPlayerHandler().getActiveModifiers());
		this.player.getPlayerHandler().getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		int councilPrivilegesCount = this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			this.player.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
			this.player.getPlayerHandler().getCouncilPrivileges().add(councilPrivilegesCount);
			gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
			// TODO aggiorno tutti
			// TODO manda scelta di privilegio
		} else {
			gameHandler.nextTurn();
		}
	}
}
