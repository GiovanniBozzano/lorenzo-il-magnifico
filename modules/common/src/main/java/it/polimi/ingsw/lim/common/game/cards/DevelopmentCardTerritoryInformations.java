package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardTerritoryInformations extends DevelopmentCardInformations
{
	private final int activationValue;
	private final List<ResourceAmount> harvestResources;

	public DevelopmentCardTerritoryInformations(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformations reward, int activationValue, List<ResourceAmount> harvestResources)
	{
		super(displayName, texturePath, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.harvestResources = new ArrayList<>(harvestResources);
	}

	@Override
	public String getInformations()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getCommonInformations());
		stringBuilder.append("\n\nHARVEST ACTIVATION VALUE: ");
		stringBuilder.append(this.activationValue);
		if (!this.harvestResources.isEmpty()) {
			stringBuilder.append("\n\nHARVEST RESOURCES:");
			for (ResourceAmount resourceAmount : this.harvestResources) {
				stringBuilder.append("\n    - ");
				stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
				stringBuilder.append(": ");
				stringBuilder.append(resourceAmount.getAmount());
			}
		}
		return stringBuilder.toString();
	}
}
