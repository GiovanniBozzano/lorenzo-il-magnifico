package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.ResourceType;

import java.io.Serializable;

public class ResourceAmount implements Serializable
{
	private final ResourceType resourceType;
	private int amount;

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

	public void setAmount(int amount)
	{
		this.amount = amount <= 0 ? 0 : amount;
	}

	@Override
	public boolean equals(Object resourceAmount)
	{
		if (!(resourceAmount instanceof ResourceAmount)) {
			return false;
		}
		return this.resourceType == ((ResourceAmount) resourceAmount).resourceType && this.amount == ((ResourceAmount) resourceAmount).amount;
	}
}
