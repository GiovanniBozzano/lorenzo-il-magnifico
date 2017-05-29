package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.network.Connection;

public class EventChurchSupport extends Event
{
	private int victoryPoints;

	public EventChurchSupport(Connection player, int victoryPoints)
	{
		super(player);
		this.victoryPoints = victoryPoints;
	}

	public int getVictoryPoints()
	{
		return this.victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints)
	{
		this.victoryPoints = victoryPoints <= 0 ? 0 : victoryPoints;
	}
}
