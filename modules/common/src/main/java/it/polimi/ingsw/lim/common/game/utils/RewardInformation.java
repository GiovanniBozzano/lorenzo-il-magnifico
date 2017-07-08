package it.polimi.ingsw.lim.common.game.utils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>This class represents a client-side reward.
 */
public class RewardInformation implements Serializable
{
	private final String actionRewardInformation;
	private final List<ResourceAmount> resourceAmounts;

	public RewardInformation(String actionRewardInformation, List<ResourceAmount> resourceAmounts)
	{
		this.actionRewardInformation = actionRewardInformation;
		this.resourceAmounts = resourceAmounts;
	}

	public String getActionRewardInformation()
	{
		return this.actionRewardInformation;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
