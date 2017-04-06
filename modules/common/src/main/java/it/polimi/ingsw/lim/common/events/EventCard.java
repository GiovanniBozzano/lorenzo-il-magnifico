package it.polimi.ingsw.lim.common.events;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;

import java.util.List;

public class EventCard extends Event
{
	private CardType[] cardTypes;
	private List<ResourceAmount[]> discountChoices;

	public EventCard(int value, CardType[] cardTypes, List<ResourceAmount[]> discountChoices)
	{
		this.value = value;
		this.cardTypes = cardTypes;
		this.discountChoices = discountChoices;
	}

	public CardType[] getCardTypes()
	{
		return cardTypes;
	}

	public List<ResourceAmount[]> getDiscountChoices()
	{
		return discountChoices;
	}
}
