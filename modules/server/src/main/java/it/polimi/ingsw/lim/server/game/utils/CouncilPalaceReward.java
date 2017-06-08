package it.polimi.ingsw.lim.server.game.utils;

import java.util.List;

public class CouncilPalaceReward
{
	private final List<ResourceAmount> resourceAmounts;

	public CouncilPalaceReward(List<ResourceAmount> resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
