package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.MarketSlot;

public class AvailableActionMarket extends AvailableAction
{
	private final MarketSlot marketSlot;

	public AvailableActionMarket(MarketSlot marketSlot)
	{
		super(ActionType.MARKET);
		this.marketSlot = marketSlot;
	}

	public MarketSlot getMarketSlot()
	{
		return this.marketSlot;
	}
}