package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.List;

public class ExpectedActionChooseRewardGetDevelopmentCard extends ExpectedAction
{
	private final List<CardType> cardTypes;
	private final Row maximumRow;
	private final List<AvailableActionGetDevelopmentCard> availableActionsGetDevelopmentCard;
	private final List<List<ResourceAmount>> discountChoices;

	public ExpectedActionChooseRewardGetDevelopmentCard(List<CardType> cardTypes, Row maximumRow, List<AvailableActionGetDevelopmentCard> availableActionsGetDevelopmentCard, List<List<ResourceAmount>> discountChoices)
	{
		super(ActionType.CHOOSE_REWARD_GET_DEVELOPMENT_CARD);
		this.cardTypes = new ArrayList<>(cardTypes);
		this.maximumRow = maximumRow;
		this.availableActionsGetDevelopmentCard = new ArrayList<>(availableActionsGetDevelopmentCard);
		this.discountChoices = new ArrayList<>(discountChoices);
	}

	public List<CardType> getCardTypes()
	{
		return this.cardTypes;
	}

	public Row getMaximumRow()
	{
		return this.maximumRow;
	}

	public List<AvailableActionGetDevelopmentCard> getAvailableActionsGetDevelopmentCard()
	{
		return this.availableActionsGetDevelopmentCard;
	}

	public List<List<ResourceAmount>> getDiscountChoices()
	{
		return this.discountChoices;
	}
}
