package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.player.Player;

public class EventFirstTurn extends Event
{
	private boolean cancelled = false;

	public EventFirstTurn(Player player)
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
