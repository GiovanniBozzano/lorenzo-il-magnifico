package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsHarvestStart;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionHarvestStart extends ActionInformationsHarvestStart implements IAction
{
	private final Connection player;

	public ActionHarvestStart(Connection player, FamilyMemberType familyMemberType)
	{
		super(familyMemberType);
		this.player = player;
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
}
