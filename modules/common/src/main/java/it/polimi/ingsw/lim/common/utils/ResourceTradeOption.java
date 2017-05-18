package it.polimi.ingsw.lim.common.utils;

public class ResourceTradeOption
{
	private final ResourceAmount[] resourceAmountsCost;
	private final ResourceAmount[] resourceAmountsGain;

	public ResourceTradeOption(ResourceAmount[] resourceAmountsCost, ResourceAmount[] resourceAmountsGain)
	{
		this.resourceAmountsCost = resourceAmountsCost;
		this.resourceAmountsGain = resourceAmountsGain;
	}

	public ResourceAmount[] getResourceAmountsCost()
	{
		return this.resourceAmountsCost;
	}

	public ResourceAmount[] getResourceAmountsGain()
	{
		return this.resourceAmountsGain;
	}
}
