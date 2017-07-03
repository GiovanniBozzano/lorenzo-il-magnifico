package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventHarvest;

/**
 * <p>Whenever you perform a Harvest action (through a Family Member or as an
 * effect of another card), increase the value of the action by {@code value}.
 */
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
