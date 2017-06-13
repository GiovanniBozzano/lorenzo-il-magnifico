package it.polimi.ingsw.lim.server.game.utils;

import it.polimi.ingsw.lim.common.game.ResourceAmount;

import java.util.List;

public class DiscountChoice
{
	private final List<ResourceAmount> resourceAmounts;

	public DiscountChoice(List<ResourceAmount> resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
