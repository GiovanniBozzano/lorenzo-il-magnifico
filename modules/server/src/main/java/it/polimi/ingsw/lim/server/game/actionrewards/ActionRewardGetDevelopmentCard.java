package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.server.game.utils.DiscountChoice;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;

public class ActionRewardGetDevelopmentCard extends ActionReward
{
	private final int value;
	private final CardType[] cardTypes;
	private final List<DiscountChoice> discountChoices;

	public ActionRewardGetDevelopmentCard(int value, CardType[] cardTypes, List<DiscountChoice> discountChoices)
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

	public CardType[] getCardTypes()
	{
		return this.cardTypes;
	}

	public List<DiscountChoice> getDiscountChoices()
	{
		return this.discountChoices;
	}
}
