package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGainResourcesServants extends Modifier<EventGainResources>
{
	private static final ModifierGainResourcesServants INSTANCE = new ModifierGainResourcesServants();

	private ModifierGainResourcesServants()
	{
		super(EventGainResources.class);
	}

	@Override
	public void apply(EventGainResources event)
	{
	}

	public ModifierGainResourcesServants getInstance()
	{
		return ModifierGainResourcesServants.INSTANCE;
	}
}
