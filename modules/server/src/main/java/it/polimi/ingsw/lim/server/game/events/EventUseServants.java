package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.network.Connection;

public class EventUseServants extends Event
{
	private int servants;

	public EventUseServants(Connection player, int servants)
	{
		super(player);
		this.servants = servants;
	}

	public int getServants()
	{
		return this.servants;
	}

	public void setServants(int servants)
	{
		this.servants = servants;
	}
}
