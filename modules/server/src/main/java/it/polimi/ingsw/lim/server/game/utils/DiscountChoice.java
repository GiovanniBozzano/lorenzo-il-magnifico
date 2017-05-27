package it.polimi.ingsw.lim.server.game.utils;

public class DiscountChoice
{
	private final ResourceAmount[] resourceAmounts;

	public DiscountChoice(ResourceAmount[] resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}

	public ResourceAmount[] getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
