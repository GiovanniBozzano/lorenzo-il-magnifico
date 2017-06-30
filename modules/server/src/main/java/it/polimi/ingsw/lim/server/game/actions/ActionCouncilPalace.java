package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsCouncilPalace;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;

public class ActionCouncilPalace extends ActionInformationsCouncilPalace implements IAction
{
	private transient final Player player;

	public ActionCouncilPalace(FamilyMemberType familyMemberType, int servants, Player player)
	{
		super(familyMemberType, servants);
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
		// get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.getFamilyMemberType(), BoardPosition.COUNCIL_PALACE, this.player.getRoom().getGameHandler().getFamilyMemberTypeValues().get(this.getFamilyMemberType()));
		eventPlaceFamilyMember.applyModifiers(this.player.getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		// check if the player has the servants he sent
		if (this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.getServants()) {
			throw new GameActionFailedException("");
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.applyModifiers(this.player.getActiveModifiers());
		int effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		if (effectiveFamilyMemberValue + effectiveServants < BoardHandler.getBoardPositionInformations(BoardPosition.COUNCIL_PALACE).getValue()) {
			throw new GameActionFailedException("");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		if (!this.player.getRoom().getGameHandler().getBoardHandler().getCouncilPalaceOrder().contains(this.player)) {
			this.player.getRoom().getGameHandler().getBoardHandler().getCouncilPalaceOrder().add(this.player);
		}
		this.player.getFamilyMembersPositions().put(this.getFamilyMemberType(), BoardPosition.COUNCIL_PALACE);
		EventGainResources eventGainResources = new EventGainResources(this.player, BoardHandler.getBoardPositionInformations(BoardPosition.COUNCIL_PALACE).getResourceAmounts(), ResourcesSource.COUNCIL_PALACE);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			this.player.getRoom().getGameHandler().setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
			this.player.getRoom().getGameHandler().sendGameUpdateExpectedAction(this.player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}
