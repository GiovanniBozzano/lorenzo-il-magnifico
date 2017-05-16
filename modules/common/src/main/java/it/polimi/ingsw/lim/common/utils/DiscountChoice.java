package it.polimi.ingsw.lim.common.utils;

public class DiscountChoice
{
	private final ResourceAmount[] resourceAmount;

	public DiscountChoice(ResourceAmount[] resourceAmount)
	{
		this.resourceAmount = resourceAmount;
	}

	public ResourceAmount[] getResourceAmount()
	{
		return this.resourceAmount;
	}
}
