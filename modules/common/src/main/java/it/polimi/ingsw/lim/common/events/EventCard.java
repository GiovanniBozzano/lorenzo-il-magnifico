package it.polimi.ingsw.lim.common.events;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public class EventCard extends Event
{
	private CardType[] cardTypes;
	private ResourceAmount[] discountedResouces;
	public EventCard(int value, CardType[] cardTypes, ResourceAmount[] discountedResources)
	{
		this.value = value;
		this.cardTypes = cardTypes;
		this.discountedResouces = discountedResources;
	}

	public CardType[] getCardTypes()
	{
		return cardTypes;
	}

	public ResourceAmount[] getDiscountedResouces()
	{
		return discountedResouces;
	}
}
