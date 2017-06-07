package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class DevelopmentCardVenture extends DevelopmentCard
{
	private final int victoryValue;

	public DevelopmentCardVenture(String displayName, int index, List<ResourceCostOption> resourceCostOptions, Reward reward, int victoryValue)
	{
		super(displayName, index, resourceCostOptions, reward);
		this.victoryValue = victoryValue;
	}

	public int getVictoryValue()
	{
		return this.victoryValue;
	}
}
