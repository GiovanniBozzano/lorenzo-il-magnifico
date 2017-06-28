package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.player.Player;

public class EventVictoryPointsCalculation extends Event
{
	private boolean countingCharacters = true;
	private boolean countingTerritories = true;
	private boolean countingVentures = true;

	public EventVictoryPointsCalculation(Player player)
	{
		super(player);
	}

	public boolean isCountingCharacters()
	{
		return this.countingCharacters;
	}

	public void setCountingCharacters(boolean countingCharacters)
	{
		this.countingCharacters = countingCharacters;
	}

	public boolean isCountingTerritories()
	{
		return this.countingTerritories;
	}

	public void setCountingTerritories(boolean countingTerritories)
	{
		this.countingTerritories = countingTerritories;
	}

	public boolean isCountingVentures()
	{
		return this.countingVentures;
	}

	public void setCountingVentures(boolean countingVentures)
	{
		this.countingVentures = countingVentures;
	}
}
