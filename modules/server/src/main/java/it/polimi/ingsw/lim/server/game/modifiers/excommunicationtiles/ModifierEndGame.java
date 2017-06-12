package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventPreVictoryPointsCalculation;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierEndGame extends Modifier<EventPreVictoryPointsCalculation>
{
	private static final ModifierEndGame INSTANCE = new ModifierEndGame();

	private ModifierEndGame()
	{
		super(EventPreVictoryPointsCalculation.class);
	}

	@Override
	public void apply(EventPreVictoryPointsCalculation event)
	{
	}

	public ModifierEndGame getInstance()
	{
		return ModifierEndGame.INSTANCE;
	}
}
