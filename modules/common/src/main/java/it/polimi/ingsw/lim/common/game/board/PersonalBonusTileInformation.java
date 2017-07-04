package it.polimi.ingsw.lim.common.game.board;

import it.polimi.ingsw.lim.common.game.ObjectInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.List;

public class PersonalBonusTileInformation extends ObjectInformation
{
	private final String playerBoardTexturePath;
	private final int productionActivationCost;
	private final List<ResourceAmount> productionInstantResources;
	private final int harvestActivationCost;
	private final List<ResourceAmount> harvestInstantResources;

	public PersonalBonusTileInformation(String texturePath, String playerBoardTexturePath, int productionActivationCost, List<ResourceAmount> productionInstantResources, int harvestActivationCost, List<ResourceAmount> harvestInstantResources)
	{
		super(texturePath);
		this.playerBoardTexturePath = playerBoardTexturePath;
		this.productionActivationCost = productionActivationCost;
		this.productionInstantResources = new ArrayList<>(productionInstantResources);
		this.harvestActivationCost = harvestActivationCost;
		this.harvestInstantResources = new ArrayList<>(harvestInstantResources);
	}

	@Override
	public String getInformation()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Production activation cost: ");
		stringBuilder.append(this.productionActivationCost);
		if (!this.productionInstantResources.isEmpty()) {
			stringBuilder.append("\n\nProduction bonus resources:\n");
		}
		stringBuilder.append(ResourceAmount.getResourcesInformation(this.productionInstantResources, true));
		stringBuilder.append("\n\nHarvest activation cost: ");
		stringBuilder.append(this.harvestActivationCost);
		if (!this.harvestInstantResources.isEmpty()) {
			stringBuilder.append("\n\nHarvest bonus resources:\n");
		}
		stringBuilder.append(ResourceAmount.getResourcesInformation(this.harvestInstantResources, true));
		return stringBuilder.toString();
	}

	public String getPlayerBoardTexturePath()
	{
		return this.playerBoardTexturePath;
	}
}
