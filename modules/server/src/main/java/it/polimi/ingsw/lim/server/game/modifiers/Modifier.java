package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.Event;

public abstract class Modifier<T extends Event>
{
	private Class<T> eventClass;
	private final String description;

	protected Modifier(Class<T> eventClass, String description)
	{
		this.eventClass = eventClass;
		this.description = description;
	}

	public abstract void apply(T event);

	public void call(Event event)
	{
		if (event.getClass() == this.eventClass) {
			this.apply(this.eventClass.cast(event));
		}
	}

	public abstract void setEventClass();

	protected void setEventClass(Class<T> eventClass)
	{
		this.eventClass = eventClass;
	}

	public String getDescription()
	{
		return this.description;
	}
}
