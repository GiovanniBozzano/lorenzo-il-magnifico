package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.MarketSlot;

public class AvailableActionMarket extends AvailableActionFamilyMember
{
	private final MarketSlot marketSlot;

	public AvailableActionMarket(FamilyMemberType familyMemberType, MarketSlot marketSlot)
	{
		super(ActionType.MARKET, familyMemberType);
		this.marketSlot = marketSlot;
	}

	public MarketSlot getMarketSlot()
	{
		return this.marketSlot;
	}
}