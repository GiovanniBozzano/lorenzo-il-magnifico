package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Resource;

public class ResourceAmount
{
	private final Resource resourceType;
	private final int amount;
	private final CardType cardTypeMultiplier;
	private final Resource resourceTypeMultiplier;
	private final int resourceAmountMultiplier;

	public ResourceAmount(Resource resourceType, int amount)
	{
		this.resourceType = resourceType;
		this.amount = amount;
		this.cardTypeMultiplier = null;
		this.resourceTypeMultiplier = null;
		this.resourceAmountMultiplier = 0;
	}

	public ResourceAmount(Resource resourceType, int amount, CardType cardTypeMultiplier)
	{
		this.resourceType = resourceType;
		this.amount = amount;
		this.cardTypeMultiplier = cardTypeMultiplier;
		this.resourceTypeMultiplier = null;
		this.resourceAmountMultiplier = 0;
	}

	public ResourceAmount(Resource resourceType, int amount, Resource resourceTypeMultiplier, int resourceAmountMultiplier)
	{
		this.resourceType = resourceType;
		this.amount = amount;
		this.cardTypeMultiplier = null;
		this.resourceTypeMultiplier = resourceTypeMultiplier;
		this.resourceAmountMultiplier = resourceAmountMultiplier;
	}

	public Resource getResourceType()
	{
		return this.resourceType;
	}

	public int getAmount()
	{
		return this.amount;
	}

	public CardType getCardTypeMultiplier()
	{
		return this.cardTypeMultiplier;
	}

	public Resource getResourceTypeMultiplier()
	{
		return this.resourceTypeMultiplier;
	}

	public int getResourceAmountMultiplier()
	{
		return this.resourceAmountMultiplier;
	}
}
