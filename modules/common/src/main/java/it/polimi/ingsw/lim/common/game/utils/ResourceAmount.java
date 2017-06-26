package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.ResourceType;

import java.io.Serializable;
import java.util.Objects;

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
		return resourceAmount instanceof ResourceAmount && this.resourceType == ((ResourceAmount) resourceAmount).resourceType && this.amount == ((ResourceAmount) resourceAmount).amount;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.resourceType, this.amount);
	}
}
