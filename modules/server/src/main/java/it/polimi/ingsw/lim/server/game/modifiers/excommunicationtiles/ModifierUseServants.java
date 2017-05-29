package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierUseServants extends Modifier<EventUseServants>
{
	private static final ModifierUseServants INSTANCE = new ModifierUseServants();

	private ModifierUseServants()
	{
		super(EventUseServants.class);
	}

	@Override
	public void apply(EventUseServants event)
	{
	}

	public ModifierUseServants getInstance()
	{
		return ModifierUseServants.INSTANCE;
	}
}
