package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventVictoryPointsCalculation;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierVictoryPointsCalculationBuildings extends Modifier<EventVictoryPointsCalculation>
{
	private static final ModifierVictoryPointsCalculationBuildings INSTANCE = new ModifierVictoryPointsCalculationBuildings();

	private ModifierVictoryPointsCalculationBuildings()
	{
		super(EventVictoryPointsCalculation.class);
	}

	@Override
	public void apply(EventVictoryPointsCalculation event)
	{
	}

	public ModifierVictoryPointsCalculationBuildings getInstance()
	{
		return ModifierVictoryPointsCalculationBuildings.INSTANCE;
	}
}
