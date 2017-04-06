package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.CardType;

import java.util.List;

public class BonusAdditionCard extends BonusAddition
{
	private CardType cardType;
	private List<ResourceAmount[]> discoutChoices;

	public BonusAdditionCard(int value, CardType cardType, List<ResourceAmount[]> discountChoices)
	{
		this.value = value;
		this.cardType = cardType;
		this.discoutChoices = discountChoices;
	}

	public CardType getCardType()
	{
		return cardType;
	}

	public List<ResourceAmount[]> getDiscoutChoices()
	{
		return discoutChoices;
	}
}
