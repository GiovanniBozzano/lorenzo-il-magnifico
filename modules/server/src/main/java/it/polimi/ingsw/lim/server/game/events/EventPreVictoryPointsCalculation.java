package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.player.Player;

public class EventPreVictoryPointsCalculation extends Event
{
	private int victoryPoints;

	public EventPreVictoryPointsCalculation(Player player)
	{
		super(player);
		this.victoryPoints = player.getPlayerResourceHandler().getResources().get(ResourceType.VICTORY_POINT);
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
