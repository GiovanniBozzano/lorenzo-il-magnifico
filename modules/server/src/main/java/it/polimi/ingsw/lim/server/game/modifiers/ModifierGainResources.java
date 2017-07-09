package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;

import java.util.ArrayList;
import java.util.List;

public class ModifierGainResources extends Modifier<EventGainResources>
{
	private final List<ResourceType> resourceTypes;
	private final int value;

	public ModifierGainResources(String description, List<ResourceType> resourceTypes, int value)
	{
		super(EventGainResources.class, description);
		this.resourceTypes = new ArrayList<>(resourceTypes);
		this.value = value;
	}

	@Override
	public void apply(EventGainResources event)
	{
		for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
			if (this.resourceTypes.contains(resourceAmount.getResourceType())) {
				resourceAmount.setAmount(resourceAmount.getAmount() + this.value);
			}
		}
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventGainResources.class);
	}
}
