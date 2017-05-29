package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGainResourcesCoins extends Modifier<EventGainResources>
{
	private static final ModifierGainResourcesCoins INSTANCE = new ModifierGainResourcesCoins();

	private ModifierGainResourcesCoins()
	{
		super(EventGainResources.class);
	}

	@Override
	public void apply(EventGainResources event)
	{
	}

	public ModifierGainResourcesCoins getInstance()
	{
		return ModifierGainResourcesCoins.INSTANCE;
	}
}
