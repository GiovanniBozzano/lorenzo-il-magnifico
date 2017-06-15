package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.util.ArrayList;
import java.util.List;

public class ExpectedActionProductionTrade extends ExpectedAction
{
	private final List<Integer> availableCards;

	public ExpectedActionProductionTrade(List<Integer> availableCards)
	{
		super(ActionType.PRODUCTION_TRADE);
		this.availableCards = new ArrayList<>(availableCards);
	}

	public List<Integer> getAvailableCards()
	{
		return this.availableCards;
	}
}
