package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventVictoryPointsCalculation;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierVictoryPointsCalculationResources extends Modifier<EventVictoryPointsCalculation>
{
	private static final ModifierVictoryPointsCalculationResources INSTANCE = new ModifierVictoryPointsCalculationResources();

	private ModifierVictoryPointsCalculationResources()
	{
		super(EventVictoryPointsCalculation.class);
	}

	@Override
	public void apply(EventVictoryPointsCalculation event)
	{
	}

	public ModifierVictoryPointsCalculationResources getInstance()
	{
		return ModifierVictoryPointsCalculationResources.INSTANCE;
	}
}
