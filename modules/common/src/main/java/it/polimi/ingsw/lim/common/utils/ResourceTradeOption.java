package it.polimi.ingsw.lim.common.utils;

public class ResourceTradeOption
{
	private final ResourceAmount[] firstResources;
	private final ResourceAmount[] secondResources;

	public ResourceTradeOption(ResourceAmount[] firstResources, ResourceAmount[] secondResources)
	{
		this.firstResources = firstResources;
		this.secondResources = secondResources;
	}

	public ResourceAmount[] getFirstResources()
	{
		return this.firstResources;
	}

	public ResourceAmount[] getSecondResources()
	{
		return this.secondResources;
	}
}
