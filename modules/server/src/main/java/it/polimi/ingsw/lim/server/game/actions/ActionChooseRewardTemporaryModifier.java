package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionChooseRewardTemporaryModifier implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;

	public ActionChooseRewardTemporaryModifier(Connection player, FamilyMemberType familyMemberType)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
	}

	@Override
	public boolean isLegal()
	{
		return false;
	}

	@Override
	public void apply()
	{
	}

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}
}
