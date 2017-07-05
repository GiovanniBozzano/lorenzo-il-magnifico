package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;

import java.util.ArrayList;
import java.util.List;

public abstract class DevelopmentCardInformation extends CardInformation
{
	private final List<ResourceCostOption> resourceCostOptions;
	private final RewardInformation reward;

	DevelopmentCardInformation(String texturePath, String displayName, List<ResourceCostOption> resourceCostOptions, RewardInformation reward)
	{
		super(texturePath, displayName);
		this.resourceCostOptions = new ArrayList<>(resourceCostOptions);
		this.reward = reward;
	}

	@Override
	String getCommonInformation()
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean firstLine = true;
		if (!this.resourceCostOptions.isEmpty()) {
			firstLine = false;
			stringBuilder.append("RESOURCE COST OPTIONS:\n==============\n");
			for (ResourceCostOption resourceCostOption : this.resourceCostOptions) {
				stringBuilder.append(resourceCostOption.getInformation(false));
				stringBuilder.append("\n==============\n");
			}
		}
		if (this.reward.getActionRewardInformation() != null || !this.reward.getResourceAmounts().isEmpty()) {
			if (!firstLine) {
				stringBuilder.append("\n\n");
			}
			stringBuilder.append("REWARD:");
		}
		if (!this.reward.getResourceAmounts().isEmpty()) {
			stringBuilder.append("\nInstant resources:\n");
			stringBuilder.append(ResourceAmount.getResourcesInformation(this.reward.getResourceAmounts(), true));
		}
		if (this.reward.getActionRewardInformation() != null) {
			stringBuilder.append("\nAction reward:\n| ");
			stringBuilder.append(this.reward.getActionRewardInformation().replace("\n", "\n| "));
		}
		return stringBuilder.toString();
	}

	public List<ResourceCostOption> getResourceCostOptions()
	{
		return this.resourceCostOptions;
	}

	public RewardInformation getReward()
	{
		return this.reward;
	}
}
