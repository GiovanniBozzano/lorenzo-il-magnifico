package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;

import java.util.List;

public class LeaderCardRewardInformation extends LeaderCardInformation
{
	private final RewardInformation reward;

	public LeaderCardRewardInformation(String texturePath, String displayName, String description, List<LeaderCardConditionsOption> conditionsOptions, RewardInformation reward)
	{
		super(texturePath, displayName, description, conditionsOptions);
		this.reward = reward;
	}

	@Override
	public String getInformation()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getCommonInformation());
		stringBuilder.append("ONCE PER ROUND ABILITY:");
		if (!this.reward.getResourceAmounts().isEmpty()) {
			stringBuilder.append("\n\nInstant resources:\n");
		}
		stringBuilder.append(ResourceAmount.getResourcesInformation(this.reward.getResourceAmounts(), true));
		if (this.reward.getActionRewardInformation() != null) {
			stringBuilder.append("\n\nAction reward:\n");
			stringBuilder.append(this.reward.getActionRewardInformation());
		}
		return stringBuilder.toString();
	}

	public RewardInformation getReward()
	{
		return this.reward;
	}
}
