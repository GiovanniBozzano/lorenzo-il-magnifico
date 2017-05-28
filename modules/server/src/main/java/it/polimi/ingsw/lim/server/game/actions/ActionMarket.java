package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.MarketSlot;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionMarket implements IAction
{
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

	public MarketSlot getMarketSlot()
	{
		return this.marketSlot;
	}
}
