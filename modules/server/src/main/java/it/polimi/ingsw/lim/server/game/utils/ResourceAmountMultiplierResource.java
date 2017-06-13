package it.polimi.ingsw.lim.server.game.utils;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.ResourceAmount;

public class ResourceAmountMultiplierResource extends ResourceAmount
{
	private final ResourceType resourceTypeMultiplier;
	private final int resourceAmountDivider;

	public ResourceAmountMultiplierResource(ResourceType resourceType, int amount, ResourceType resourceTypeMultiplier, int amountDivider)
	{
		super(resourceType, amount);
		this.resourceTypeMultiplier = resourceTypeMultiplier;
		this.resourceAmountDivider = amountDivider;
	}

	public ResourceType getResourceTypeMultiplier()
	{
		return this.resourceTypeMultiplier;
	}

	public int getResourceAmountDivider()
	{
		return this.resourceAmountDivider;
	}
}
