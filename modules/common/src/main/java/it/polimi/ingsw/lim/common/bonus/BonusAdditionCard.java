package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public class BonusAdditionCard extends BonusAddition
{
	private final CardType cardType;
	private final ResourceAmount[][] discoutChoices;

	public BonusAdditionCard(int value, CardType cardType, ResourceAmount[][] discountChoices)
	{
		this.value = value;
		this.cardType = cardType;
		this.discoutChoices = discountChoices;
	}

	public CardType getCardType()
	{
		return cardType;
	}

	public ResourceAmount[][] getDiscoutChoices()
	{
		return discoutChoices;
	}
}
