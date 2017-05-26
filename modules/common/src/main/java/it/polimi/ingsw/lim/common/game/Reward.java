package it.polimi.ingsw.lim.common.game;

import it.polimi.ingsw.lim.common.events.Event;

public class Reward
{
	private final Event[] events;
	private final ResourceAmount[] resourceAmounts;

	public Reward(Event[] events, ResourceAmount[] resourceAmounts)
	{
		this.events = events;
		this.resourceAmounts = resourceAmounts;
	}

	public Event[] getEvents()
	{
		return this.events;
	}

	public ResourceAmount[] getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
