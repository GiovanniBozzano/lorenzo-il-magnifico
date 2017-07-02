package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

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
		stringBuilder.append(this.getCommonInformations());
		stringBuilder.append("ONCE PER ROUND ABILITY:");
		if (!this.reward.getResourceAmounts().isEmpty()) {
			stringBuilder.append("\n\nInstant resources:");
		}
		stringBuilder.append(ResourceAmount.getResourcesInformations(this.reward.getResourceAmounts(), true));
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
