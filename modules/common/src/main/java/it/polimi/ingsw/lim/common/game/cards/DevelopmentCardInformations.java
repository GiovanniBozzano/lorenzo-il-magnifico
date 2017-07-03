package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

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
					stringBuilder.append("\nRequired resources:\n");
					stringBuilder.append(ResourceAmount.getResourcesInformations(resourceCostOption.getRequiredResources(), true));
				}
				if (!resourceCostOption.getSpentResources().isEmpty()) {
					stringBuilder.append("\nSpent resources:\n");
					stringBuilder.append(ResourceAmount.getResourcesInformations(resourceCostOption.getSpentResources(), true));
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
			stringBuilder.append("\nInstant resources:\n");
			stringBuilder.append(ResourceAmount.getResourcesInformations(this.reward.getResourceAmounts(), true));
		}
		if (this.reward.getActionRewardInformations() != null) {
			stringBuilder.append("\nAction reward:\n| ");
			stringBuilder.append(this.reward.getActionRewardInformations().replace("\n", "\n| "));
		}
		return stringBuilder.toString();
	}

	public List<ResourceCostOption> getResourceCostOptions()
	{
		return this.resourceCostOptions;
	}

	public RewardInformations getReward()
	{
		return this.reward;
	}
}
