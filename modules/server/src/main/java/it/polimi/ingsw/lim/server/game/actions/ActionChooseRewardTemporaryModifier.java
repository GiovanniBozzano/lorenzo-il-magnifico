package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseRewardTemporaryModifier;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionChooseRewardTemporaryModifier extends ActionInformationChooseRewardTemporaryModifier implements IAction
{
	private final transient Player player;

	public ActionChooseRewardTemporaryModifier(FamilyMemberType familyMemberType, Player player)
	{
		super(familyMemberType);
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
		if (this.player.getRoom().getGameHandler().getExpectedAction() != ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check whether the chosen family member is colored
		if (this.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
			throw new GameActionFailedException("This action cannot be performed because the selected Family Member is the Neutral one");
		}
	}

	@Override
	public void apply()
	{
		Modifier<EventPlaceFamilyMember> modifier = new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "")
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() != ActionChooseRewardTemporaryModifier.this.getFamilyMemberType()) {
					return;
				}
				event.setFamilyMemberValue(6);
			}

			@Override
			public void setEventClass()
			{
				super.setEventClass(EventPlaceFamilyMember.class);
			}
		};
		this.player.getTemporaryModifiers().add(modifier);
		this.player.getActiveModifiers().add(modifier);
		this.player.getRoom().getGameHandler().setExpectedAction(null);
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " chose a temporary modifier for his " + this.getFamilyMemberType().name().toLowerCase() + " family member");
		this.player.getPlayerResourceHandler().convertTemporaryResources();
		this.player.getRoom().getGameHandler().sendGameUpdate(this.player);
	}
}
