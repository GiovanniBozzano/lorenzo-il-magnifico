package it.polimi.ingsw.lim.common.game.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourceCostOption implements Serializable
{
	private final List<ResourceAmount> requiredResources = new ArrayList<>();
	private final List<ResourceAmount> spentResources = new ArrayList<>();

	public ResourceCostOption(List<ResourceAmount> requiredResources, List<ResourceAmount> spentResources)
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
		new ResourceCostOption(resourceCostOption.requiredResources, resourceCostOption.spentResources);
	}

	public List<ResourceAmount> getRequiredResources()
	{
		return this.requiredResources;
	}

	public List<ResourceAmount> getSpentResources()
	{
		return this.spentResources;
	}

	@Override
	public boolean equals(Object resourceCostOption)
	{
		if (!(resourceCostOption instanceof ResourceCostOption)) {
			return false;
		}
		List<ResourceAmount> temporaryRequiredResources = new ArrayList<>();
		temporaryRequiredResources.addAll(((ResourceCostOption) resourceCostOption).requiredResources);
		for (ResourceAmount resourceAmount : this.requiredResources) {
			if (!temporaryRequiredResources.contains(resourceAmount)) {
				return false;
			}
			temporaryRequiredResources.remove(resourceAmount);
		}
		List<ResourceAmount> temporarySpentResources = new ArrayList<>();
		temporarySpentResources.addAll(((ResourceCostOption) resourceCostOption).spentResources);
		for (ResourceAmount resourceAmount : this.spentResources) {
			if (!temporarySpentResources.contains(resourceAmount)) {
				return false;
			}
			temporarySpentResources.remove(resourceAmount);
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.requiredResources, this.spentResources);
	}
}
