package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.network.Connection;

public class EventEndGame extends Event
{
	private int victoryPoints;

	public EventEndGame(Connection player, int victoryPoints)
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
		this.victoryPoints = (this.victoryPoints - victoryPoints <= 0 ? 0 : this.victoryPoints - victoryPoints);
	}
}
