package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.EnumResource;

import java.util.Map;
import java.util.Set;

public class CardPrice extends CardAttribute
{
	private Map<EnumResource, Integer> resources;

	public CardPrice put(EnumResource resource, Integer amount)
	{
		resources.put(resource, amount);
		return this;
	}

	public CardPrice put(ResourceAmount[] resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			resources.put(resourceAmount.getResourceType(), resourceAmount.getAmount());
		}
		return this;
	}

	public Set<EnumResource> keySet()
	{
		return resources.keySet();
	}
}
