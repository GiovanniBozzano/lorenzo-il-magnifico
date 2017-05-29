package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventStartProduction;

/**
 * <p>Whenever you perform a Production action (through a Family Member or as
 * an efect of another card), increase the value of the action by {@code
 * value}.
 */
public class ModifierStartProduction extends Modifier<EventStartProduction>
{
	private final int value;

	public ModifierStartProduction(int value)
	{
		super(EventStartProduction.class);
		this.value = value;
	}

	@Override
	public void apply(EventStartProduction event)
	{
		event.setActionValue(event.getActionValue() + this.value);
	}
}
