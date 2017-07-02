package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class DevelopmentCardInformations extends CardInformations
{
	private final List<ResourceCostOption> resourceCostOptions;
	private final RewardInformations reward;

	DevelopmentCardInformations(String texturePath, String displayName, List<ResourceCostOption> resourceCostOptions, RewardInformations reward)
	{
		super(texturePath, displayName);
		this.resourceCostOptions = new ArrayList<>(resourceCostOptions);
		this.reward = reward;
	}

	@Override
	String getCommonInformations()
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean firstLine = true;
		if (!this.resourceCostOptions.isEmpty()) {
			firstLine = false;
			stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
			for (ResourceCostOption resourceCostOption : this.resourceCostOptions) {
				if (!resourceCostOption.getRequiredResources().isEmpty()) {
					stringBuilder.append("\nRequired resources:");
					for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
						stringBuilder.append("\n    - ");
						stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
				}
				if (!resourceCostOption.getSpentResources().isEmpty()) {
					stringBuilder.append("\nSpent resources:");
					for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
						stringBuilder.append("\n    - ");
						stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
				}
				stringBuilder.append("\n==============");
			}
		}
		if (this.reward.getActionRewardInformations() != null || !this.reward.getResourceAmounts().isEmpty()) {
			if (!firstLine) {
				stringBuilder.append("\n\n");
			}
			stringBuilder.append("REWARD:");
		}
		if (!this.reward.getResourceAmounts().isEmpty()) {
			stringBuilder.append("\nInstant resources:");
			for (ResourceAmount resourceAmount : this.reward.getResourceAmounts()) {
				stringBuilder.append("\n    - ");
				stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
				stringBuilder.append(": ");
				stringBuilder.append(resourceAmount.getAmount());
			}
		}
		if (this.reward.getActionRewardInformations() != null) {
			stringBuilder.append("\nAction reward:\n| ");
			stringBuilder.append(this.reward.getActionRewardInformations().replace("\n", "\n| "));
		}
		return stringBuilder.toString();
	}
}
