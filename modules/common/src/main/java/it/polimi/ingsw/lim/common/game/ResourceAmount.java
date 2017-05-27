package it.polimi.ingsw.lim.common.game;

import it.polimi.ingsw.lim.common.enums.ResourceType;

public class ResourceAmount
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
		this.amount = (this.amount - amount <= 0 ? 0 : this.amount - amount);
	}
}
