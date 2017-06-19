package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.player.Player;

public class EventUseServants extends Event
{
	private int servants;

	public EventUseServants(Player player, int servants)
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
		this.servants = servants <= 0 ? 0 : servants;
	}
}
