package it.polimi.ingsw.lim.common.events;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public class EventCard extends Event
{
	private final CardType[] cardTypes;
	private final ResourceAmount[][] discountChoices;

	public EventCard(int value, CardType[] cardTypes, ResourceAmount[][] discountChoices)
	{
		this.value = value;
		this.cardTypes = cardTypes;
		this.discountChoices = discountChoices;
	}

	public CardType[] getCardTypes()
	{
		return this.cardTypes;
	}

	public ResourceAmount[][] getDiscountChoices()
	{
		return this.discountChoices;
	}
}
