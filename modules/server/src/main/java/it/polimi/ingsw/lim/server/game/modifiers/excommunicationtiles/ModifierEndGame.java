package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventEndGame;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierEndGame extends Modifier<EventEndGame>
{
	private static final ModifierEndGame INSTANCE = new ModifierEndGame();

	private ModifierEndGame()
	{
		super(EventEndGame.class);
	}

	@Override
	public void apply(EventEndGame event)
	{
	}

	public ModifierEndGame getInstance()
	{
		return ModifierEndGame.INSTANCE;
	}
}
