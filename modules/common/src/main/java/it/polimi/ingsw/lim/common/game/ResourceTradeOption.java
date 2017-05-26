package it.polimi.ingsw.lim.common.game;

public class ResourceTradeOption
{
	private final ResourceAmount[] employedResources;
	private final ResourceAmount[] producedResources;

	public ResourceTradeOption(ResourceAmount[] employedResources, ResourceAmount[] producedResources)
	{
		this.employedResources = employedResources;
		this.producedResources = producedResources;
	}

	public ResourceAmount[] getemployedResources()
	{
		return this.employedResources;
	}

	public ResourceAmount[] getproducedResources()
	{
		return this.producedResources;
	}
}
