package it.polimi.ingsw.lim.server.game.utils;

import it.polimi.ingsw.lim.common.game.ResourceAmount;

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

	public List<ResourceAmount> getRequiredResources()
	{
		return this.requiredResources;
	}

	public List<ResourceAmount> getSpentResources()
	{
		return this.spentResources;
	}
}
