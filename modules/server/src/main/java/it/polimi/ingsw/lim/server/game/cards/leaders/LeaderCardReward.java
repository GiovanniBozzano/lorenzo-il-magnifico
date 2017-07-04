package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformation;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardRewardInformation;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;
import it.polimi.ingsw.lim.server.enums.LeaderCardType;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class LeaderCardReward extends LeaderCard
{
	private final Reward reward;
	private boolean activated = false;

	public LeaderCardReward(int index, String texturePath, String displayName, String description, List<LeaderCardConditionsOption> conditionsOptions, Reward reward)
	{
		super(index, texturePath, displayName, description, LeaderCardType.REWARD, conditionsOptions);
		this.reward = reward;
	}

	@Override
	public LeaderCardInformation getInformation()
	{
		return new LeaderCardRewardInformation(this.getTexturePath(), this.getDisplayName(), this.getDescription(), this.getConditionsOptions(), new RewardInformation(this.reward.getActionReward() == null ? null : this.reward.getActionReward().getDescription(), this.reward.getResourceAmounts()));
	}

	public Reward getReward()
	{
		return this.reward;
	}

	public boolean isActivated()
	{
		return this.activated;
	}

	public void setActivated(boolean activated)
	{
		this.activated = activated;
	}
}
