package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.utils.ResourceTradeOption;

public class DevelopmentCardBuilding extends Card
{
	private final ResourceAmount[] buildResources;
	private final ResourceAmount[] instantResources;
	private final int activationCost;
	private final ResourceTradeOption[] resourceTradeOptions;

	public DevelopmentCardBuilding(String displayName, int index, ResourceAmount[] buildResources, ResourceAmount[] instantResources, int activationCost, ResourceTradeOption[] resourceTradeOptions)
	{
		super(displayName, index);
		this.buildResources = buildResources;
		this.instantResources = instantResources;
		this.activationCost = activationCost;
		this.resourceTradeOptions = resourceTradeOptions;
	}

	public ResourceAmount[] getBuildResources()
	{
		return this.buildResources;
	}

	public ResourceAmount[] getInstantResources()
	{
		return this.instantResources;
	}

	public int getActivationCost()
	{
		return this.activationCost;
	}

	public ResourceTradeOption[] getResourceTradeOptions()
	{
		return this.resourceTradeOptions;
	}
}
