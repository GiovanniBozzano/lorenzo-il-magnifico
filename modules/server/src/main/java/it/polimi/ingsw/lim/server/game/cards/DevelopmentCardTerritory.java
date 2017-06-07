package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class DevelopmentCardTerritory extends DevelopmentCard
{
	private final int activationValue;
	private final ResourceAmount[] harvestResources;

	public DevelopmentCardTerritory(String displayName, int index, List<ResourceCostOption> resourceCostOptions, Reward reward, int activationValue, ResourceAmount[] harvestResources)
	{
		super(displayName, index, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.harvestResources = harvestResources;
	}

	public int getActivationValue()
	{
		return this.activationValue;
	}

	public ResourceAmount[] getHarvestResources()
	{
		return this.harvestResources;
	}
}

