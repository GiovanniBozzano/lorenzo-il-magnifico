package it.polimi.ingsw.lim.common.game.utils;

import java.io.Serializable;
import java.util.List;

public class RewardInformations implements Serializable
{
	private final String actionRewardInformations;
	private final List<ResourceAmount> resourceAmounts;

	public RewardInformations(String actionRewardInformations, List<ResourceAmount> resourceAmounts)
	{
		this.actionRewardInformations = actionRewardInformations;
		this.resourceAmounts = resourceAmounts;
	}

	public String getActionRewardInformations()
	{
		return this.actionRewardInformations;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
