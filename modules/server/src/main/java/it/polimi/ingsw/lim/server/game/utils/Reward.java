package it.polimi.ingsw.lim.server.game.utils;

import it.polimi.ingsw.lim.common.game.ResourceAmount;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionReward;

import java.util.List;

public class Reward
{
	private final ActionReward actionReward;
	private final List<ResourceAmount> resourceAmounts;

	public Reward(ActionReward actionReward, List<ResourceAmount> resourceAmounts)
	{
		this.actionReward = actionReward;
		this.resourceAmounts = resourceAmounts;
	}

	public ActionReward getActionReward()
	{
		return this.actionReward;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
