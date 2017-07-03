package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ResourceAmount implements Serializable
{
	private final ResourceType resourceType;
	private int amount;

	public ResourceAmount(ResourceType resourceType, int amount)
	{
		this.resourceType = resourceType;
		this.amount = amount;
	}

	@Override
	public boolean equals(Object resourceAmount)
	{
		return resourceAmount instanceof ResourceAmount && this.resourceType == ((ResourceAmount) resourceAmount).resourceType && this.amount == ((ResourceAmount) resourceAmount).amount;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.resourceType, this.amount);
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

	String getInformations()
	{
		return "- " + CommonUtils.getResourcesTypesNames().get(this.resourceType) + ": " + this.amount;
	}

	public static String getResourcesInformations(List<ResourceAmount> resourceAmounts, boolean indented)
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
			stringBuilder.append(resourceAmount.getInformations());
		}
		return stringBuilder.toString();
	}
}
