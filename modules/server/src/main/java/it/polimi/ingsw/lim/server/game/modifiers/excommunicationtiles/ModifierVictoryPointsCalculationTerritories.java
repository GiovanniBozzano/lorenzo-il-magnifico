package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventVictoryPointsCalculation;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierVictoryPointsCalculationTerritories extends Modifier<EventVictoryPointsCalculation>
{
	private static final ModifierVictoryPointsCalculationTerritories INSTANCE = new ModifierVictoryPointsCalculationTerritories();

	private ModifierVictoryPointsCalculationTerritories()
	{
		super(EventVictoryPointsCalculation.class);
	}

	@Override
	public void apply(EventVictoryPointsCalculation event)
	{
	}

	public ModifierVictoryPointsCalculationTerritories getInstance()
	{
		return ModifierVictoryPointsCalculationTerritories.INSTANCE;
	}
}
