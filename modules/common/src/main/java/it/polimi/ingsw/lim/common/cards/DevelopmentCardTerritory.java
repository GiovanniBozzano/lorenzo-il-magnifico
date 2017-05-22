package it.polimi.ingsw.lim.common.cards;

import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public class DevelopmentCardTerritory extends Card
{
	private final ResourceAmount[] instantResources;
	private final int activationCost;
	private final ResourceAmount[] harvestResources;

	public DevelopmentCardTerritory(String displayName, ResourceAmount[] instantResources, int activationCost, ResourceAmount[] harvestResources)
	{
		super(displayName);
		this.instantResources = instantResources;
		this.activationCost = activationCost;
		this.harvestResources = harvestResources;
	}

	public ResourceAmount[] getInstantResources()
	{
		return this.instantResources;
	}

	public int getActivationCost()
	{
		return this.activationCost;
	}

	public ResourceAmount[] getHarvestResources()
	{
		return this.harvestResources;
	}
}

