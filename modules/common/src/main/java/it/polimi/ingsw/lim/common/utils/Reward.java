package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.events.Event;

public class Reward
{
	private final Event[] events;
	private final ResourceAmount[] resources;

	public Reward(Event[] events, ResourceAmount[] resources)
	{
		this.events = events;
		this.resources = resources;
	}

	public Event[] getEvents()
	{
		return this.events;
	}

	public ResourceAmount[] getResources()
	{
		return this.resources;
	}
}
