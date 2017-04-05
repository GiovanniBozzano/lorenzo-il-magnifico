package it.polimi.ingsw.lim.common.utils;

public class ResourceCostOption
{
	private ResourceAmount[] resourceCondition;
	private ResourceAmount[] resourceCost;

	public ResourceCostOption(ResourceAmount[] resourceCondition, ResourceAmount[] resourceCost)
	{
		this.resourceCondition = resourceCondition;
		this.resourceCost = resourceCost;
	}

	public ResourceAmount[] getResourceCondition()
	{
		return resourceCondition;
	}

	public ResourceAmount[] getResourceCost()
	{
		return resourceCost;
	}
}
