package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class LeaderCardReward extends LeaderCard
{
	private final Reward reward;
	private boolean activated = false;

	public LeaderCardReward(String displayName, int index, List<LeaderCardConditionsOption> conditionsOptions, String description, Reward reward)
	{
		super(displayName, index, conditionsOptions, description);
		this.reward = reward;
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
