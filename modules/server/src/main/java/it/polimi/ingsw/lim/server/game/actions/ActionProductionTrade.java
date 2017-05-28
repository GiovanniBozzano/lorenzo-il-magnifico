package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsProductionTrade;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionProductionTrade extends ActionInformationsProductionTrade implements IAction
{
	private final Connection player;

	public ActionProductionTrade(Connection player, FamilyMemberType familyMemberType, int developmentCardBuildingIndex)
	{
		super(familyMemberType, developmentCardBuildingIndex);
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