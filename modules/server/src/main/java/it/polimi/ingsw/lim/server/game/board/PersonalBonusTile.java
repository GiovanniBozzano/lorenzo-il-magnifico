package it.polimi.ingsw.lim.server.game.board;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PersonalBonusTile
{
	PERSONAL_BONUS_TILES_1(0, "", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.SERVANT, 1), new ResourceAmount(ResourceType.COIN, 2))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.MILITARY_POINT, 1)))),
	PERSONAL_BONUS_TILES_2(1, "", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.SERVANT, 1), new ResourceAmount(ResourceType.MILITARY_POINT, 2))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.COIN, 1)))),
	PERSONAL_BONUS_TILES_3(2, "", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.COIN, 1))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.SERVANT, 1)))),
	PERSONAL_BONUS_TILES_4(3, "", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.SERVANT, 2), new ResourceAmount(ResourceType.COIN, 1))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.MILITARY_POINT, 1)))),
	PERSONAL_BONUS_TILES_5(4, "", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.WOOD, 1))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.MILITARY_POINT, 1), new ResourceAmount(ResourceType.SERVANT, 2))));
	private final int index;
	private final String texturePath;
	private final int productionActivationCost;
	private final List<ResourceAmount> productionInstantResources;
	private final int harvestActivationCost;
	private final List<ResourceAmount> harvestInstantResources;

	PersonalBonusTile(int index, String texturePath, int productionActivationCost, List<ResourceAmount> productionInstantResources, int harvestActivationCost, List<ResourceAmount> harvestInstantResources)
	{
		this.index = index;
		this.texturePath = texturePath;
		this.productionActivationCost = productionActivationCost;
		this.productionInstantResources = productionInstantResources;
		this.harvestActivationCost = harvestActivationCost;
		this.harvestInstantResources = harvestInstantResources;
	}

	public static PersonalBonusTile fromIndex(int index)
	{
		for (PersonalBonusTile personalBonusTile : PersonalBonusTile.values()) {
			if (personalBonusTile.getIndex() == index) {
				return personalBonusTile;
			}
		}
		return null;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public int getProductionActivationCost()
	{
		return this.productionActivationCost;
	}

	public List<ResourceAmount> getProductionInstantResources()
	{
		return this.productionInstantResources;
	}

	public int getHarvestActivationCost()
	{
		return this.harvestActivationCost;
	}

	public List<ResourceAmount> getHarvestInstantResources()
	{
		return this.harvestInstantResources;
	}
}
