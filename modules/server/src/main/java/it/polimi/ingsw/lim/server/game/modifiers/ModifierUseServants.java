package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventUseServants;

public class ModifierUseServants extends Modifier<EventUseServants>
{
	public ModifierUseServants(String description)
	{
		super(EventUseServants.class, description);
	}

	@Override
	public void apply(EventUseServants event)
	{
		event.setServants(event.getServants() / 2);
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventUseServants.class);
	}
}
