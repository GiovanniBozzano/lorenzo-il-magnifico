package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.ResourceType;

public class ResourceAmount
{
	private final ResourceType resourceType;
	private final int amount;

	public ResourceAmount(ResourceType resourceType, int amount)
	{
		this.resourceType = resourceType;
		this.amount = amount;
	}

	public ResourceType getResourceType()
	{
		return this.resourceType;
	}

	public int getAmount()
	{
		return this.amount;
	}
}
