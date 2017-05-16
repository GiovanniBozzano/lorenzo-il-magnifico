package it.polimi.ingsw.lim.common.utils;

public class DiscountChoice
{
	private final ResourceAmount[] resourceAmounts;

	public DiscountChoice(ResourceAmount[] resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}

	public ResourceAmount[] getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
