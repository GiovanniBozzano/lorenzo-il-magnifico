package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGainResourcesStoneWood extends Modifier<EventGainResources>
{
	private static final ModifierGainResourcesStoneWood INSTANCE = new ModifierGainResourcesStoneWood();

	private ModifierGainResourcesStoneWood()
	{
		super(EventGainResources.class);
	}

	@Override
	public void apply(EventGainResources event)
	{
	}

	public ModifierGainResourcesStoneWood getInstance()
	{
		return ModifierGainResourcesStoneWood.INSTANCE;
	}
}
