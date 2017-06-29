package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.List;

public class LeaderCardRewardInformations extends LeaderCardInformations
{
	private final RewardInformations reward;

	public LeaderCardRewardInformations(String texturePath, String displayName, String description, List<LeaderCardConditionsOption> conditionsOptions, RewardInformations reward)
	{
		super(texturePath, displayName, description, conditionsOptions);
		this.reward = reward;
	}

	@Override
	public String getInformations()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getLeaderCardCommonInformaitons());
		stringBuilder.append("ONCE PER ROUND ABILITY:");
		if (!this.reward.getResourceAmounts().isEmpty()) {
			stringBuilder.append("\n\nInstant resources:");
		}
		for (ResourceAmount resourceAmount : this.reward.getResourceAmounts()) {
			stringBuilder.append('\n');
			stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
			stringBuilder.append(": ");
			stringBuilder.append(resourceAmount.getAmount());
		}
		if (this.reward.getActionRewardInformations() != null) {
			stringBuilder.append("\n\nAction reward:\n");
			stringBuilder.append(this.reward.getActionRewardInformations());
		}
		return stringBuilder.toString();
	}

	public RewardInformations getReward()
	{
		return this.reward;
	}
}
