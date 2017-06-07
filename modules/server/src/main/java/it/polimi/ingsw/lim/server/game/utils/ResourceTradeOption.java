package it.polimi.ingsw.lim.server.game.utils;

public class ResourceTradeOption
{
	private final ResourceAmount[] employedResources;
	private final ResourceAmount[] producedResources;

	public ResourceTradeOption(ResourceAmount[] employedResources, ResourceAmount[] producedResources)
	{
		this.employedResources = employedResources;
		this.producedResources = producedResources;
	}

	public ResourceAmount[] getEmployedResources()
	{
		return this.employedResources;
	}

	public ResourceAmount[] getProducedResources()
	{
		return this.producedResources;
	}
}
