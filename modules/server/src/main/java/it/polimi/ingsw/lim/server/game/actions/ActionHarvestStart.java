package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionHarvestStart implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;

	public ActionHarvestStart(Connection player, FamilyMemberType familyMemberType)
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
