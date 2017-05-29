package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventStartProduction;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierStartProduction extends Modifier<EventStartProduction>
{
	private static final ModifierStartProduction INSTANCE = new ModifierStartProduction();

	private ModifierStartProduction()
	{
		super(EventStartProduction.class);
	}

	@Override
	public void apply(EventStartProduction event)
	{
	}

	public ModifierStartProduction getInstance()
	{
		return ModifierStartProduction.INSTANCE;
	}
}
