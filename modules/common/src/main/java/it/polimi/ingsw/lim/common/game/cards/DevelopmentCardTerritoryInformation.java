package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardTerritoryInformation extends DevelopmentCardInformation
{
	private final int activationValue;
	private final List<ResourceAmount> harvestResources;

	public DevelopmentCardTerritoryInformation(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformation reward, int activationValue, List<ResourceAmount> harvestResources)
	{
		super(displayName, texturePath, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.harvestResources = new ArrayList<>(harvestResources);
	}

	@Override
	public String getInformation()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getCommonInformation());
		stringBuilder.append("\n\nHARVEST ACTIVATION VALUE: ");
		stringBuilder.append(this.activationValue);
		if (!this.harvestResources.isEmpty()) {
			stringBuilder.append("\n\nHARVEST RESOURCES:\n");
			stringBuilder.append(ResourceAmount.getResourcesInformation(this.harvestResources, true));
		}
		return stringBuilder.toString();
	}
}
