package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.EnumResource;

public class ResourceAmount
{
	private EnumResource resourceType;
	private int amount;

	public ResourceAmount(EnumResource resourceType, int amount)
	{
		this.resourceType = resourceType;
		this.amount = amount;
	}

	public EnumResource getResourceType()
	{
		return resourceType;
	}

	public int getAmount()
	{
		return amount;
	}
}
