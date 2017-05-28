package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.network.Connection;

public class EventVictoryPointsCalculation extends Event
{
	private boolean countingTerritories = true;
	private boolean countingCharacters = true;
	private boolean countingVentures = true;
	private int victoryPoints;

	public EventVictoryPointsCalculation(Connection player, int victoryPoints)
	{
		super(player);
		this.victoryPoints = victoryPoints;
	}

	public boolean isCountingTerritories()
	{
		return this.countingTerritories;
	}

	public void setCountingTerritories(boolean countingTerritories)
	{
		this.countingTerritories = countingTerritories;
	}

	public boolean isCountingCharacters()
	{
		return this.countingCharacters;
	}

	public void setCountingCharacters(boolean countingCharacters)
	{
		this.countingCharacters = countingCharacters;
	}

	public boolean isCountingVentures()
	{
		return this.countingVentures;
	}

	public void setCountingVentures(boolean countingVentures)
	{
		this.countingVentures = countingVentures;
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
