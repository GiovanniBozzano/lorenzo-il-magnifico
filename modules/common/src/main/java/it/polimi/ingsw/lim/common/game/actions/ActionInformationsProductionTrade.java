package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;

import java.util.HashMap;
import java.util.Map;

public class ActionInformationsProductionTrade extends ActionInformations
{
	private final Map<Integer, ResourceTradeOption> chosenDevelopmentCardsBuilding;

	public ActionInformationsProductionTrade(Map<Integer, ResourceTradeOption> chosenDevelopmentCardsBuilding)
	{
		super(ActionType.PRODUCTION_TRADE);
		this.chosenDevelopmentCardsBuilding = new HashMap<>(chosenDevelopmentCardsBuilding);
	}

	public Map<Integer, ResourceTradeOption> getChosenDevelopmentCardsBuilding()
	{
		return this.chosenDevelopmentCardsBuilding;
	}
}
