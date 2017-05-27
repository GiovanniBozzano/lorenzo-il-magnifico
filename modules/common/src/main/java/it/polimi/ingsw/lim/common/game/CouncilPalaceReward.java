package it.polimi.ingsw.lim.common.game;

public class CouncilPalaceReward
{
	private ResourceAmount[] resourceAmounts;

	public CouncilPalaceReward(ResourceAmount[] resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}

	public ResourceAmount[] getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
