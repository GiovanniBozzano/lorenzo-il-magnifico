package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.events.Event;

public class Reward
{
	private Event[] events;
	private ResourceAmount[] resources;

	public Reward(Event[] events, ResourceAmount[] resources)
	{
		this.events = events;
		this.resources = resources;
	}

	public Event[] getEvents()
	{
		return events;
	}

	public ResourceAmount[] getResources()
	{
		return resources;
	}
}
