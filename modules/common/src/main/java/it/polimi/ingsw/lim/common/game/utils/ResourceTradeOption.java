package it.polimi.ingsw.lim.common.game.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourceTradeOption implements Serializable
{
	private final List<ResourceAmount> employedResources = new ArrayList<>();
	private final List<ResourceAmount> producedResources = new ArrayList<>();

	public ResourceTradeOption(List<ResourceAmount> employedResources, List<ResourceAmount> producedResources)
	{
		for (ResourceAmount resourceAmount : employedResources) {
			this.employedResources.add(new ResourceAmount(resourceAmount));
		}
		for (ResourceAmount resourceAmount : producedResources) {
			this.producedResources.add(new ResourceAmount(resourceAmount));
		}
	}

	@Override
	public boolean equals(Object resourceTradeOption)
	{
		if (!(resourceTradeOption instanceof ResourceTradeOption)) {
			return false;
		}
		List<ResourceAmount> temporaryEmployedResources = new ArrayList<>();
		temporaryEmployedResources.addAll(((ResourceTradeOption) resourceTradeOption).employedResources);
		for (ResourceAmount resourceAmount : this.employedResources) {
			if (!temporaryEmployedResources.contains(resourceAmount)) {
				return false;
			}
			temporaryEmployedResources.remove(resourceAmount);
		}
		List<ResourceAmount> temporaryProducedResources = new ArrayList<>();
		temporaryProducedResources.addAll(((ResourceTradeOption) resourceTradeOption).producedResources);
		for (ResourceAmount resourceAmount : this.producedResources) {
			if (!temporaryProducedResources.contains(resourceAmount)) {
				return false;
			}
			temporaryProducedResources.remove(resourceAmount);
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.employedResources, this.producedResources);
	}

	public String getInformation(boolean isFirstLine)
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (!this.employedResources.isEmpty()) {
			stringBuilder.append("\nEmployed resources:\n");
			stringBuilder.append(ResourceAmount.getResourcesInformation(this.employedResources, true));
		}
		if (!this.producedResources.isEmpty()) {
			if (!isFirstLine || !this.employedResources.isEmpty()) {
				stringBuilder.append('\n');
			}
			stringBuilder.append("\nProduced cards:\n");
			stringBuilder.append(ResourceAmount.getResourcesInformation(this.producedResources, true));
		}
		return stringBuilder.toString();
	}

	public List<ResourceAmount> getEmployedResources()
	{
		return this.employedResources;
	}

	public List<ResourceAmount> getProducedResources()
	{
		return this.producedResources;
	}
}
