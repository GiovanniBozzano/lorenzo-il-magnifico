package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.network.Connection;

public class EventStartHarvest extends Event
{
	private int actionValue;

	public EventStartHarvest(Connection player, int actionValue)
	{
		super(player);
		this.actionValue = actionValue;
	}

	public int getActionValue()
	{
		return this.actionValue;
	}

	public void setActionValue(int actionValue)
	{
		this.actionValue = actionValue;
	}
}
