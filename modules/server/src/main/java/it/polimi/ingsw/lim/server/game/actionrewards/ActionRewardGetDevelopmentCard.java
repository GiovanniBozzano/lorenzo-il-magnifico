package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.server.enums.ActionRewardType;
import it.polimi.ingsw.lim.server.game.utils.DiscountChoice;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardGetDevelopmentCard extends ActionReward
{
	private final int value;
	private final CardType[] cardTypes;
	private final DiscountChoice[] discountChoices;

	public ActionRewardGetDevelopmentCard(int value, CardType[] cardTypes, DiscountChoice[] discountChoices)
	{
		super(ActionRewardType.GET_DEVELOPMENT_CARD);
		this.value = value;
		this.cardTypes = cardTypes;
		this.discountChoices = discountChoices;
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

	public DiscountChoice[] getDiscountChoices()
	{
		return this.discountChoices;
	}
}
