package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.server.game.utils.Reward;

public class DevelopmentCardVenture extends Card
{
	private final ResourceTradeOption[] resourceTradeOptions;
	private final Reward instantReward;
	private final int victoryValue;

	public DevelopmentCardVenture(String displayName, ResourceTradeOption[] resourceTradeOptions, Reward instantReward, int victoryValue)
	{
		super(displayName);
		this.resourceTradeOptions = resourceTradeOptions;
		this.instantReward = instantReward;
		this.victoryValue = victoryValue;
	}

	public ResourceTradeOption[] getResourceTradeOptions()
	{
		return this.resourceTradeOptions;
	}

	public Reward getInstantReward()
	{
		return this.instantReward;
	}

	public int getVictoryValue()
	{
		return this.victoryValue;
	}
}
