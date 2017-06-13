package it.polimi.ingsw.lim.server.game.utils;

import it.polimi.ingsw.lim.common.game.ResourceAmount;

import java.util.List;

public class CouncilPalaceReward
{
	private final int index;
	private final List<ResourceAmount> resourceAmounts;

	public CouncilPalaceReward(int index, List<ResourceAmount> resourceAmounts)
	{
		this.index = index;
		this.resourceAmounts = resourceAmounts;
	}

	public int getIndex()
	{
		return this.index;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
