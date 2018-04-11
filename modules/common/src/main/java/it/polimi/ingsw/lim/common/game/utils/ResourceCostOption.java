package it.polimi.ingsw.lim.common.game.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>This class represents a single "resource cost" option.
 */
public class ResourceCostOption implements Serializable
{
	private final List<ResourceAmount> requiredResources = new ArrayList<>();
	private final List<ResourceAmount> spentResources = new ArrayList<>();

	private ResourceCostOption(List<ResourceAmount> requiredResources, List<ResourceAmount> spentResources)
	{
		for (ResourceAmount resourceAmount : requiredResources) {
			this.requiredResources.add(new ResourceAmount(resourceAmount));
		}
		for (ResourceAmount resourceAmount : spentResources) {
			this.spentResources.add(new ResourceAmount(resourceAmount));
		}
	}

	public ResourceCostOption(ResourceCostOption resourceCostOption)
	{
		this(resourceCostOption.requiredResources, resourceCostOption.spentResources);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.requiredResources, this.spentResources);
	}

	@Override
	public boolean equals(Object resourceCostOption)
	{
		if (!(resourceCostOption instanceof ResourceCostOption)) {
			return false;
		}
		List<ResourceAmount> temporaryRequiredResources = new ArrayList<>(((ResourceCostOption) resourceCostOption).requiredResources);
		for (ResourceAmount resourceAmount : this.requiredResources) {
			if (!temporaryRequiredResources.contains(resourceAmount)) {
				return false;
			}
			temporaryRequiredResources.remove(resourceAmount);
		}
		List<ResourceAmount> temporarySpentResources = new ArrayList<>(((ResourceCostOption) resourceCostOption).spentResources);
		for (ResourceAmount resourceAmount : this.spentResources) {
			if (!temporarySpentResources.contains(resourceAmount)) {
				return false;
			}
			temporarySpentResources.remove(resourceAmount);
		}
		return true;
	}

	public String getInformation(boolean newLine)
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (!this.requiredResources.isEmpty()) {
			if (newLine) {
				stringBuilder.append("\n");
			}
			stringBuilder.append("Required resources:\n");
			stringBuilder.append(ResourceAmount.getResourcesInformation(this.requiredResources, true));
		}
		if (!this.spentResources.isEmpty()) {
			if (!this.requiredResources.isEmpty() || newLine) {
				stringBuilder.append("\n");
			}
			stringBuilder.append("Spent resources:\n");
			stringBuilder.append(ResourceAmount.getResourcesInformation(this.spentResources, true));
		}
		return stringBuilder.toString();
	}

	public List<ResourceAmount> getRequiredResources()
	{
		return this.requiredResources;
	}

	public List<ResourceAmount> getSpentResources()
	{
		return this.spentResources;
	}
}
