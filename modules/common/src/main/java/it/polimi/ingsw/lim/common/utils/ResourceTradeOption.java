package it.polimi.ingsw.lim.common.utils;

public class ResourceTradeOption
{
	private ResourceAmount[] firstResources;
	private ResourceAmount[] secondResources;

	public ResourceTradeOption(ResourceAmount[] firstResources, ResourceAmount[] secondResources)
	{
		this.firstResources = firstResources;
		this.secondResources = secondResources;
	}

	public ResourceAmount[] getFirstResources()
	{
		return firstResources;
	}

	public ResourceAmount[] getSecondResources()
	{
		return secondResources;
	}
}
