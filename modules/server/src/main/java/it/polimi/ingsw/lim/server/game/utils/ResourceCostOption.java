package it.polimi.ingsw.lim.server.game.utils;

import java.util.List;

public class ResourceCostOption
{
	private List<ResourceAmount> resourcesAmount;

	public ResourceCostOption(List<ResourceAmount> resourcesAmount)
	{
		this.resourcesAmount = resourcesAmount;
	}

	public List<ResourceAmount> getResourcesAmount()
	{
		return this.resourcesAmount;
	}
}
