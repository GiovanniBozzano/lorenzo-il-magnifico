package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventVictoryPointsCalculation;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierVictoryPointsCalculationMilitaryPoints extends Modifier<EventVictoryPointsCalculation>
{
	private static final ModifierVictoryPointsCalculationMilitaryPoints INSTANCE = new ModifierVictoryPointsCalculationMilitaryPoints();

	private ModifierVictoryPointsCalculationMilitaryPoints()
	{
		super(EventVictoryPointsCalculation.class);
	}

	@Override
	public void apply(EventVictoryPointsCalculation event)
	{
	}

	public ModifierVictoryPointsCalculationMilitaryPoints getInstance()
	{
		return ModifierVictoryPointsCalculationMilitaryPoints.INSTANCE;
	}
}
