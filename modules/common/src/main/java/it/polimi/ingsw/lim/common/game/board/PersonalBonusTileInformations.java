package it.polimi.ingsw.lim.common.game.board;

import it.polimi.ingsw.lim.common.game.ObjectInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.List;

public class PersonalBonusTileInformations extends ObjectInformations
{
	private final String playerBoardTexturePath;
	private final int productionActivationCost;
	private final List<ResourceAmount> productionInstantResources;
	private final int harvestActivationCost;
	private final List<ResourceAmount> harvestInstantResources;

	public PersonalBonusTileInformations(String texturePath, String playerBoardTexturePath, int productionActivationCost, List<ResourceAmount> productionInstantResources, int harvestActivationCost, List<ResourceAmount> harvestInstantResources)
	{
		super(texturePath);
		this.playerBoardTexturePath = playerBoardTexturePath;
		this.productionActivationCost = productionActivationCost;
		this.productionInstantResources = new ArrayList<>(productionInstantResources);
		this.harvestActivationCost = harvestActivationCost;
		this.harvestInstantResources = new ArrayList<>(harvestInstantResources);
	}

	@Override
	public String getInformations()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Production activation cost: ");
		stringBuilder.append(this.productionActivationCost);
		if (!this.productionInstantResources.isEmpty()) {
			stringBuilder.append("\n\nProduction bonus resources:");
		}
		stringBuilder.append(ResourceAmount.getResourcesInformations(this.productionInstantResources, true));
		stringBuilder.append("\n\nHarvest activation cost: ");
		stringBuilder.append(this.harvestActivationCost);
		if (!this.harvestInstantResources.isEmpty()) {
			stringBuilder.append("\n\nHarvest bonus resources:");
		}
		stringBuilder.append(ResourceAmount.getResourcesInformations(this.harvestInstantResources, true));
		return stringBuilder.toString();
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
