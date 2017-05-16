package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;

public class ResourceAmount
{
	private final ResourceType resourceType;
	private final int amount;
	private final CardType cardTypeMultiplier;
	private final ResourceType resourceTypeMultiplier;
	private final int resourceAmountDivider;

	public ResourceAmount(ResourceType resourceType, int amount)
	{
		this.resourceType = resourceType;
		this.amount = amount;
		this.cardTypeMultiplier = null;
		this.resourceTypeMultiplier = null;
		this.resourceAmountDivider = 0;
	}

	public ResourceAmount(ResourceType resourceType, int amount, CardType cardTypeMultiplier)
	{
		this.resourceType = resourceType;
		this.amount = amount;
		this.cardTypeMultiplier = cardTypeMultiplier;
		this.resourceTypeMultiplier = null;
		this.resourceAmountDivider = 0;
	}

	public ResourceAmount(ResourceType resourceType, int amount, ResourceType resourceTypeMultiplier, int resourceAmountDivider)
	{
		this.resourceType = resourceType;
		this.amount = amount;
		this.cardTypeMultiplier = null;
		this.resourceTypeMultiplier = resourceTypeMultiplier;
		this.resourceAmountDivider = resourceAmountDivider;
	}

	public ResourceType getResourceType()
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

	public ResourceType getResourceTypeMultiplier()
	{
		return this.resourceTypeMultiplier;
	}

	public int getResourceAmountDivider()
	{
		return this.resourceAmountDivider;
	}
}
