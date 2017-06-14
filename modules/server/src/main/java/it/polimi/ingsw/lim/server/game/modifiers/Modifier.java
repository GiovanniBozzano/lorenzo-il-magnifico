package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.Event;

public abstract class Modifier<T extends Event>
{
	private final Class<T> eventClass;

	protected Modifier(Class<T> eventClass)
	{
		this.eventClass = eventClass;
	}

	public abstract void apply(T event);

	public void call(Event event)
	{
		if (event.getClass() == this.eventClass) {
			this.apply(this.eventClass.cast(event));
		}
	}

	public Class<T> getEventClass()
	{
		return this.eventClass;
	}
}
