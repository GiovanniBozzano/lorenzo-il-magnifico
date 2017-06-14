package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpectedActionChooseRewardGetDevelopmentCard extends ExpectedAction
{
	private final Map<CardType, Row> maximumRows;
	private final List<AvailableActionChooseRewardGetDevelopmentCard> availableActions;
	private final List<List<ResourceAmount>> discountChoices;

	public ExpectedActionChooseRewardGetDevelopmentCard(Map<CardType, Row> maximumRow, List<AvailableActionChooseRewardGetDevelopmentCard> availableActions, List<List<ResourceAmount>> discountChoices)
	{
		super(ActionType.CHOOSE_REWARD_GET_DEVELOPMENT_CARD);
		this.maximumRows = new HashMap<>(maximumRow);
		this.availableActions = new ArrayList<>(availableActions);
		this.discountChoices = new ArrayList<>(discountChoices);
	}

	public Map<CardType, Row> getMaximumRows()
	{
		return this.maximumRows;
	}

	public List<AvailableActionChooseRewardGetDevelopmentCard> getAvailableActions()
	{
		return this.availableActions;
	}

	public List<List<ResourceAmount>> getDiscountChoices()
	{
		return this.discountChoices;
	}
}
