package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.player.Player;

/**
 * <p>This class represents a permanent effect bound to a specific {@link
 * Player}.
 *
 * @param <T> the {@link Event} type on which this {@link Modifier} is applied.
 */
public abstract class Modifier<T extends Event>
{
	private final String description;
	private Class<T> eventClass;

	protected Modifier(Class<T> eventClass, String description)
	{
		this.eventClass = eventClass;
		this.description = description;
	}

	/**
	 * <p>Checks if this {@link Modifier} can be applied to the given {@link
	 * Event} and, if positive, applies it.
	 *
	 * @param event the current fired {@link Event}.
	 */
	public void call(Event event)
	{
		if (event.getClass() == this.eventClass) {
			this.apply(this.eventClass.cast(event));
		}
	}

	/**
	 * <p>Applies this {@link Modifier} effect on the {@link Event}.
	 *
	 * @param event the current fired {@link Event}.
	 */
	public abstract void apply(T event);

	/**
	 * <p>Sets the {@link Event} {@link Class} on which this {@link Modifier} is
	 * applied.
	 *
	 * <p>This is needed when the {@link Modifier} is loaded from a file.
	 */
	public abstract void setEventClass();

	/**
	 * <p>Builds the description of this {@link Modifier}.
	 *
	 * @return the {@link String} representing the description.
	 */
	public String getDescription()
	{
		return this.description;
	}

	/**
	 * <p>Sets the {@link Event} {@link Class} on which this {@link Modifier} is
	 * applied.
	 *
	 * @param eventClass the {@link Event} {@link Class} to set.
	 */
	protected void setEventClass(Class<T> eventClass)
	{
		this.eventClass = eventClass;
	}
}
