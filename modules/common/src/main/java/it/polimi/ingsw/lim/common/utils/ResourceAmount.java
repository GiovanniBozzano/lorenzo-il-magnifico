package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Resource;

public class ResourceAmount
{
	private Resource resourceType;
	private int amount;
	private CardType cardTypeMultiplier;
	private Resource resourceTypeMultiplier;
	private int resourceAmountMultiplier;

	public ResourceAmount(Resource resourceType, int amount)
	{
		this.resourceType = resourceType;
		this.amount = amount;
	}

	public ResourceAmount(Resource resourceType, int amount, CardType cardTypeMultiplier)
	{
		this.resourceType = resourceType;
		this.amount = amount;
		this.cardTypeMultiplier = cardTypeMultiplier;
	}

	public ResourceAmount(Resource resourceType, int amount, Resource resourceTypeMultiplier, int resourceAmountMultiplier)
	{
		this.resourceType = resourceType;
		this.amount = amount;
		this.resourceTypeMultiplier = resourceTypeMultiplier;
		this.resourceAmountMultiplier = resourceAmountMultiplier;
	}

	public Resource getResourceType()
	{
		return resourceType;
	}

	public int getAmount()
	{
		return amount;
	}

	public CardType getCardTypeMultiplier()
	{
		return cardTypeMultiplier;
	}

	public Resource getResourceTypeMultiplier()
	{
		return resourceTypeMultiplier;
	}

	public int getResourceAmountMultiplier()
	{
		return resourceAmountMultiplier;
	}
}
