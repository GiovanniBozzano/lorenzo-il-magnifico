package it.polimi.ingsw.lim.server.game.board;

import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.List;
import java.util.NoSuchElementException;

public class PersonalBonusTile
{
	private final int index;
	private final String texturePath;
	private final String playerBoardTexturePath;
	private final int productionActivationCost;
	private final List<ResourceAmount> productionInstantResources;
	private final int harvestActivationCost;
	private final List<ResourceAmount> harvestInstantResources;

	PersonalBonusTile(int index, String texturePath, String playerBoardTexturePath, int productionActivationCost, List<ResourceAmount> productionInstantResources, int harvestActivationCost, List<ResourceAmount> harvestInstantResources)
	{
		this.index = index;
		this.texturePath = texturePath;
		this.playerBoardTexturePath = playerBoardTexturePath;
		this.productionActivationCost = productionActivationCost;
		this.productionInstantResources = productionInstantResources;
		this.harvestActivationCost = harvestActivationCost;
		this.harvestInstantResources = harvestInstantResources;
	}

	public static PersonalBonusTile fromIndex(int index)
	{
		for (PersonalBonusTile personalBonusTile : BoardHandler.getPersonalBonusTiles()) {
			if (personalBonusTile.index == index) {
				return personalBonusTile;
			}
		}
		throw new NoSuchElementException();
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public String getPlayerBoardTexturePath()
	{
		return this.playerBoardTexturePath;
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
