package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;

public class EventGainResources implements IEvent
{
	private final ResourceAmount[] resourceAmounts;

	public EventGainResources(ResourceAmount[] resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}

	@Override
	public void apply()
	{
	}
}
