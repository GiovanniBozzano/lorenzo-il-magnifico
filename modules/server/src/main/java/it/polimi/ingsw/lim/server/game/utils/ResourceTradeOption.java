package it.polimi.ingsw.lim.server.game.utils;

import java.util.List;

public class ResourceTradeOption
{
	private final List<ResourceAmount> employedResources;
	private final List<ResourceAmount> producedResources;

	public ResourceTradeOption(List<ResourceAmount> employedResources, List<ResourceAmount> producedResources)
	{
		this.employedResources = employedResources;
		this.producedResources = producedResources;
	}

	public List<ResourceAmount> getEmployedResources()
	{
		return this.employedResources;
	}

	public List<ResourceAmount> getProducedResources()
	{
		return this.producedResources;
	}
}
