package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * <p>This class represents an amount of resources. It is used to represent a
 * condition or a reward.
 */
public class ResourceAmount implements Serializable
{
	private final ResourceType resourceType;
	private int amount;

	public ResourceAmount(ResourceType resourceType, int amount)
	{
		this.resourceType = resourceType;
		this.amount = amount;
	}

	public ResourceAmount(ResourceAmount resourceAmount)
	{
		this(resourceAmount.resourceType, resourceAmount.amount);
	}

	public static String getResourcesInformation(List<ResourceAmount> resourceAmounts, boolean indented)
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean firstLine = true;
		for (ResourceAmount resourceAmount : resourceAmounts) {
			if (!firstLine) {
				stringBuilder.append('\n');
			} else {
				firstLine = false;
			}
			if (indented) {
				stringBuilder.append("    ");
			}
			stringBuilder.append(resourceAmount.getInformation());
		}
		return stringBuilder.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.resourceType, this.amount);
	}

	@Override
	public boolean equals(Object resourceAmount)
	{
		return resourceAmount instanceof ResourceAmount && this.resourceType == ((ResourceAmount) resourceAmount).resourceType && this.amount == ((ResourceAmount) resourceAmount).amount;
	}

	public ResourceType getResourceType()
	{
		return this.resourceType;
	}

	public int getAmount()
	{
		return this.amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount <= 0 ? 0 : amount;
	}

	String getInformation()
	{
		return "- " + CommonUtils.getResourcesTypesNames().get(this.resourceType) + ": " + this.amount;
	}
}
