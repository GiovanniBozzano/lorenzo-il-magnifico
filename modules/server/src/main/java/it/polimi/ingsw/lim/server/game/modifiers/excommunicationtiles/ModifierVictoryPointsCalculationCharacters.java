package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventVictoryPointsCalculation;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierVictoryPointsCalculationCharacters extends Modifier<EventVictoryPointsCalculation>
{
	private static final ModifierVictoryPointsCalculationCharacters INSTANCE = new ModifierVictoryPointsCalculationCharacters();

	private ModifierVictoryPointsCalculationCharacters()
	{
		super(EventVictoryPointsCalculation.class);
	}

	@Override
	public void apply(EventVictoryPointsCalculation event)
	{
	}

	public ModifierVictoryPointsCalculationCharacters getInstance()
	{
		return ModifierVictoryPointsCalculationCharacters.INSTANCE;
	}
}
