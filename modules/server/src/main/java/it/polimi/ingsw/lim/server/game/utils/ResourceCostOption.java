package it.polimi.ingsw.lim.server.game.utils;

import java.util.List;

public class ResourceCostOption
{
	private final List<ResourceAmount> requiredResources;
	private final List<ResourceAmount> spentResources;

	public ResourceCostOption(List<ResourceAmount> requiredResources, List<ResourceAmount> spentResources)
	{
		this.requiredResources = requiredResources;
		this.spentResources = spentResources;
	}

	public List<ResourceAmount> getrequiredResources()
	{
		return this.requiredResources;
	}

	public List<ResourceAmount> getspentResources()
	{
		return this.spentResources;
	}
}
