package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.MarketSlot;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsMarket;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionMarket extends ActionInformationsMarket implements IAction
{
	private Connection player;

	public ActionMarket(Connection player, FamilyMemberType familyMemberType, MarketSlot marketSlot)
	{
		super(familyMemberType, marketSlot);
		this.player = player;
	}

	@Override
	public void isLegal()
	{
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
