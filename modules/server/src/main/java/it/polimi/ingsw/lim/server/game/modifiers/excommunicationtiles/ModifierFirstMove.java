package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventFirstMove;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierFirstMove extends Modifier<EventFirstMove>
{
	private static final ModifierFirstMove INSTANCE = new ModifierFirstMove();

	private ModifierFirstMove()
	{
		super(EventFirstMove.class);
	}

	@Override
	public void apply(EventFirstMove event)
	{
	}

	public ModifierFirstMove getInstance()
	{
		return ModifierFirstMove.INSTANCE;
	}
}
