package it.polimi.ingsw.lim.common.events;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.utils.DiscountChoice;

public class EventCard extends Event
{
	private final CardType[] cardTypes;
	private final DiscountChoice[] discountChoices;

	public EventCard(int value, CardType[] cardTypes, DiscountChoice[] discountChoices)
	{
		this.value = value;
		this.cardTypes = cardTypes;
		this.discountChoices = discountChoices;
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
