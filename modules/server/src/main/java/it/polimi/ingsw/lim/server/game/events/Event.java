package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.network.Connection;

public abstract class Event
{
	private final Connection player;

	public Event(Connection player)
	{
		this.player = player;
	}

	public Connection getPlayer()
	{
		return this.player;
	}
}
