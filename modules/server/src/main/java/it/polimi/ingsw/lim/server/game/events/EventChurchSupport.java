package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.player.Player;

public class EventChurchSupport extends Event
{
	private int victoryPoints = 0;

	public EventChurchSupport(Player player)
	{
		super(player);
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
