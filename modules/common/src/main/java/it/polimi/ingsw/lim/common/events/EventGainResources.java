package it.polimi.ingsw.lim.common.events;

import it.polimi.ingsw.lim.common.game.ResourceAmount;

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
