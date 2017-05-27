package it.polimi.ingsw.lim.common.game;

import it.polimi.ingsw.lim.common.actionrewards.ActionReward;

public class Reward
{
	private final ActionReward actionReward;
	private final ResourceAmount[] resourceAmounts;

	public Reward(ActionReward actionReward, ResourceAmount[] resourceAmounts)
	{
		this.actionReward = actionReward;
		this.resourceAmounts = resourceAmounts;
	}

	public ActionReward getActionReward()
	{
		return this.actionReward;
	}

	public ResourceAmount[] getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
