package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventStartHarvest;

/**
 * <p>Whenever you perform a Harvest action (through a Family Member or as an
 * effect of another card), increase the value of the action by {@code
 * value}.
 */
public class ModifierStartHarvest extends Modifier<EventStartHarvest>
{
	private final int value;

	public ModifierStartHarvest(String description, int value)
	{
		super(EventStartHarvest.class, description);
		this.value = value;
	}

	@Override
	public void apply(EventStartHarvest event)
	{
		event.setActionValue(event.getActionValue() + this.value);
	}
}
