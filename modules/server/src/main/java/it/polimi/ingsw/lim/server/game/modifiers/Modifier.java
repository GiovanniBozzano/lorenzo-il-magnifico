package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.Event;

public abstract class Modifier<T extends Event>
{
	private final Class<T> eventClass;

	public Modifier(Class<T> eventClass)
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
}
