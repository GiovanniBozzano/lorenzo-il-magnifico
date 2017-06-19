package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.player.Player;

public class EventChurchSupport extends Event
{
	private int victoryPoints;

	public EventChurchSupport(Player player, int victoryPoints)
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
