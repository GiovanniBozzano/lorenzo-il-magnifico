package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventHarvest;

public class ModifierHarvest extends Modifier<EventHarvest>
{
	private final int value;

	public ModifierHarvest(String description, int value)
	{
		super(EventHarvest.class, description);
		this.value = value;
	}

	@Override
	public void apply(EventHarvest event)
	{
		event.setActionValue(event.getActionValue() + this.value);
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventHarvest.class);
	}
}
