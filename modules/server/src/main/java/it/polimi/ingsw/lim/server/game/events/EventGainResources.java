package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

public class EventGainResources extends Event
{
	private final ResourceAmount[] resourceAmounts;
	private final int sourcesCount;
	private final ResourcesSource resourcesSource;

	public EventGainResources(Connection player, ResourceAmount[] resourceAmounts, int sourcesCount, ResourcesSource resourcesSource)
	{
		super(player);
		this.resourceAmounts = resourceAmounts;
		this.sourcesCount = sourcesCount;
		this.resourcesSource = resourcesSource;
	}

	public ResourceAmount[] getResourceAmounts()
	{
		return this.resourceAmounts;
	}

	public int getSourcesCount()
	{
		return this.sourcesCount;
	}

	public ResourcesSource getResourcesSource()
	{
		return resourcesSource;
	}
}
