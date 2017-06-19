package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.util.List;

public class EventGainResources extends Event
{
	private final List<ResourceAmount> resourceAmounts;
	private final ResourcesSource resourcesSource;

	public EventGainResources(Player player, List<ResourceAmount> resourceAmounts, ResourcesSource resourcesSource)
	{
		super(player);
		this.resourceAmounts = resourceAmounts;
		this.resourcesSource = resourcesSource;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}

	public ResourcesSource getResourcesSource()
	{
		return this.resourcesSource;
	}
}
