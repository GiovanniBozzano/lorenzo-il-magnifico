package it.polimi.ingsw.lim.common.game;

import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.io.Serializable;
import java.util.List;

public class CouncilPalaceRewardInformations implements Serializable
{
	private final int index;
	private final List<ResourceAmount> resourceAmounts;

	public CouncilPalaceRewardInformations(int index, List<ResourceAmount> resourceAmounts)
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
