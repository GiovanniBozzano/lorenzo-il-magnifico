package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventProductionStart;

/**
 * <p>This class represents a modifier with the following effect:
 *
 * <p>Whenever you perform a Production action (through a Family Member or as an
 * effect of another card), increase the value of the action by {@code value}.
 */
public class ModifierProductionStart extends Modifier<EventProductionStart>
{
	private final int value;

	public ModifierProductionStart(String description, int value)
	{
		super(EventProductionStart.class, description);
		this.value = value;
	}

	@Override
	public void apply(EventProductionStart event)
	{
		event.setActionValue(event.getActionValue() + this.value);
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventProductionStart.class);
	}
}
