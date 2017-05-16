package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.utils.DiscountChoice;

public class BonusAdditionCard extends BonusAddition
{
	private final CardType cardType;
	private final DiscountChoice[] discoutChoices;

	public BonusAdditionCard(int value, CardType cardType, DiscountChoice[] discountChoices)
	{
		this.value = value;
		this.cardType = cardType;
		this.discoutChoices = discountChoices;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public DiscountChoice[] getDiscoutChoices()
	{
		return this.discoutChoices;
	}
}
