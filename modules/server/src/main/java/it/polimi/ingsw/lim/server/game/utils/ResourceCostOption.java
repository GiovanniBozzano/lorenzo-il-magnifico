package it.polimi.ingsw.lim.server.game.utils;

public class ResourceCostOption
{
	private ResourceAmount[] resourcesAmount;

	public ResourceCostOption(ResourceAmount[] resourcesAmount)
	{
		this.resourcesAmount = resourcesAmount;
	}

	public ResourceAmount[] getResourcesAmount()
	{
		return this.resourcesAmount;
	}
}
