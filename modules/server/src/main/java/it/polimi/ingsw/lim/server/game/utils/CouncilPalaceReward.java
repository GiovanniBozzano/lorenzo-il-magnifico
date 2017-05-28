package it.polimi.ingsw.lim.server.game.utils;

public class CouncilPalaceReward
{
	private final ResourceAmount[] resourceAmounts;

	public CouncilPalaceReward(ResourceAmount[] resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}

	public ResourceAmount[] getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
