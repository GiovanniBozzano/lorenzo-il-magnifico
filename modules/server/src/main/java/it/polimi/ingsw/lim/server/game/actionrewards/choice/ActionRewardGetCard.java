package it.polimi.ingsw.lim.server.game.actionrewards.choice;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardChoice;
import it.polimi.ingsw.lim.server.game.utils.DiscountChoice;

public class ActionRewardGetCard extends ActionRewardChoice
{
	private final int value;
	private final CardType[] cardTypes;
	private final DiscountChoice[] discountChoices;

	public ActionRewardGetCard(int value, CardType[] cardTypes, DiscountChoice[] discountChoices)
	{
		this.value = value;
		this.cardTypes = cardTypes;
		this.discountChoices = discountChoices;
	}

	@Override
	public void apply()
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
