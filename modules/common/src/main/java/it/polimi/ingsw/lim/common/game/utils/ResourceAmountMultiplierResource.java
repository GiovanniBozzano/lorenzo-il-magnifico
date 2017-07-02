package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.Objects;

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

	@Override
	public boolean equals(Object resourceAmount)
	{
		return resourceAmount instanceof ResourceAmountMultiplierResource && this.getResourceType() == ((ResourceAmountMultiplierResource) resourceAmount).getResourceType() && this.getAmount() == ((ResourceAmountMultiplierResource) resourceAmount).getAmount() && this.resourceTypeMultiplier == ((ResourceAmountMultiplierResource) resourceAmount).resourceTypeMultiplier && this.resourceAmountDivider == ((ResourceAmountMultiplierResource) resourceAmount).resourceAmountDivider;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.getResourceType(), this.getAmount(), this.resourceTypeMultiplier, this.resourceAmountDivider);
	}

	@Override
	public String getInformations()
	{
		return "- " + CommonUtils.getResourcesTypesNames().get(this.getResourceType()) + ": " + this.getAmount() + " x " + this.resourceAmountDivider + " " + CommonUtils.getResourcesTypesNames().get(this.resourceTypeMultiplier);
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
