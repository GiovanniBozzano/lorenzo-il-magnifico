package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.network.Connection;

public class EventFirstMove extends Event
{
	private boolean cancelled = false;

	public EventFirstMove(Connection player)
	{
		super(player);
	}

	public boolean isCancelled()
	{
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}
}
