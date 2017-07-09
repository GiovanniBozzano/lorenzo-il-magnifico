package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventFirstTurn;

public class ModifierFirstTurn extends Modifier<EventFirstTurn>
{
	public ModifierFirstTurn(String description)
	{
		super(EventFirstTurn.class, description);
	}

	@Override
	public void apply(EventFirstTurn event)
	{
		event.setCancelled(true);
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventFirstTurn.class);
	}
}
