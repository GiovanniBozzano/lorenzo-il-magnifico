package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.MarketSlot;

public class ActionInformationsMarket extends ActionInformations
{
	private FamilyMemberType familyMemberType;
	private MarketSlot marketSlot;

	public ActionInformationsMarket(FamilyMemberType familyMemberType, MarketSlot marketSlot)
	{
		super(ActionType.MARKET);
		this.familyMemberType = familyMemberType;
		this.marketSlot = marketSlot;
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
