package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpectedActionProductionTrade extends ExpectedAction
{
	private final Map<Integer, List<ResourceTradeOption>> availableCards;

	public ExpectedActionProductionTrade(Map<Integer, List<ResourceTradeOption>> availableCards)
	{
		super(ActionType.PRODUCTION_TRADE);
		this.availableCards = new HashMap<>(availableCards);
	}

	public Map<Integer, List<ResourceTradeOption>> getAvailableCards()
	{
		return this.availableCards;
	}
}
