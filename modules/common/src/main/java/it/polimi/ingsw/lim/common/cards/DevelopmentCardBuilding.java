package it.polimi.ingsw.lim.common.cards;

import it.polimi.ingsw.lim.common.game.ResourceAmount;
import it.polimi.ingsw.lim.common.game.ResourceTradeOption;

public class DevelopmentCardBuilding extends Card
{
	private final ResourceAmount[] buildResources;
	private final ResourceAmount[] instantResources;
	private final int activationCost;
	private final ResourceTradeOption[] resourceTradeOptions;

	public DevelopmentCardBuilding(String displayName, ResourceAmount[] buildResources, ResourceAmount[] instantResources, int activationCost, ResourceTradeOption[] resourceTradeOptions)
	{
		super(displayName);
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
