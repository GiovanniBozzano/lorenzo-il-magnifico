package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;

public class ActionRewardGetDevelopmentCard extends ActionReward
{
	private final int value;
	private final List<CardType> cardTypes;
	private final List<List<ResourceAmount>> discountChoices;

	public ActionRewardGetDevelopmentCard(int value, List<CardType> cardTypes, List<List<ResourceAmount>> discountChoices)
	{
		super(ActionType.CHOOSE_REWARD_GET_DEVELOPMENT_CARD);
		this.value = value;
		this.cardTypes = cardTypes;
		this.discountChoices = new ArrayList<>(discountChoices);
	}

	@Override
	public void apply(Connection player)
	{
	}

	public int getValue()
	{
		return this.value;
	}

	public List<CardType> getCardTypes()
	{
		return this.cardTypes;
	}

	public List<List<ResourceAmount>> getDiscountChoices()
	{
		return this.discountChoices;
	}
}
