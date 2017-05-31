package it.polimi.ingsw.lim.server.game.board;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;

public enum PersonalBonusTile
{
	PERSONAL_BONUS_TILES_STANDARD(1, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 1), new ResourceAmount(ResourceType.COIN, 2) }, 1, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.SERVANT, 1) }),
	PERSONAL_BONUS_TILES_1(1, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 1), new ResourceAmount(ResourceType.COIN, 2) }, 1, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.MILITARY_POINT, 1) }),
	PERSONAL_BONUS_TILES_2(1, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 1), new ResourceAmount(ResourceType.MILITARY_POINT, 2) }, 1, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.COIN, 1) }),
	PERSONAL_BONUS_TILES_3(1, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.COIN, 1) }, 1, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.SERVANT, 1) }),
	PERSONAL_BONUS_TILES_4(1, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 2), new ResourceAmount(ResourceType.COIN, 1) }, 1, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.MILITARY_POINT, 1) });
	private final int workActivationCost;
	private final ResourceAmount[] workInstantResources;
	private final int harvestActivationCost;
	private final ResourceAmount[] harvestInstantResources;

	PersonalBonusTile(int workActivationCost, ResourceAmount[] workInstantResources, int harvestActivationCost, ResourceAmount[] harvestInstantResources)
	{
		this.workActivationCost = workActivationCost;
		this.workInstantResources = workInstantResources;
		this.harvestActivationCost = harvestActivationCost;
		this.harvestInstantResources = harvestInstantResources;
	}

	public int getWorkActivationCost()
	{
		return this.workActivationCost;
	}

	public ResourceAmount[] getWorkInstantResources()
	{
		return this.workInstantResources;
	}

	public int getHarvestActivationCost()
	{
		return this.harvestActivationCost;
	}

	public ResourceAmount[] getHarvestInstantResources()
	{
		return this.harvestInstantResources;
	}
}
