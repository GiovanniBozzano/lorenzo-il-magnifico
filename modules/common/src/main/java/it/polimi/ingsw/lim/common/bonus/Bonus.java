package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.events.IEvent;

public abstract class Bonus<T extends IEvent>
{
	private final Class<T> eventClass;

	public Bonus(Class<T> eventClass)
	{
		this.eventClass = eventClass;
	}

	public abstract T apply(T event);

	public Class<T> getEventClass()
	{
		return this.eventClass;
	}
}
