package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventStartHarvest;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierStartHarvest extends Modifier<EventStartHarvest>
{
	private static final ModifierStartHarvest INSTANCE = new ModifierStartHarvest();

	private ModifierStartHarvest()
	{
		super(EventStartHarvest.class);
	}

	@Override
	public void apply(EventStartHarvest event)
	{
	}

	public ModifierStartHarvest getInstance()
	{
		return ModifierStartHarvest.INSTANCE;
	}
}
