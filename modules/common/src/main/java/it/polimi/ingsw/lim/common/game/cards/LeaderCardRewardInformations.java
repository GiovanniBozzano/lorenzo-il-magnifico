package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

import java.util.List;

public class LeaderCardRewardInformations extends LeaderCardInformations
{
	private final RewardInformations reward;

	public LeaderCardRewardInformations(int index, String texturePath, String description, List<LeaderCardConditionsOption> conditionsOptions, RewardInformations reward)
	{
		super(index, texturePath, description, conditionsOptions);
		this.reward = reward;
	}

	public RewardInformations getReward()
	{
		return this.reward;
	}
}
