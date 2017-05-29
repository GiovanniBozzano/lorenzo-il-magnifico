package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventVictoryPointsCalculation;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierVictoryPointsCalculationVentures extends Modifier<EventVictoryPointsCalculation>
{
	private static final ModifierVictoryPointsCalculationVentures INSTANCE = new ModifierVictoryPointsCalculationVentures();

	private ModifierVictoryPointsCalculationVentures()
	{
		super(EventVictoryPointsCalculation.class);
	}

	@Override
	public void apply(EventVictoryPointsCalculation event)
	{
	}

	public ModifierVictoryPointsCalculationVentures getInstance()
	{
		return ModifierVictoryPointsCalculationVentures.INSTANCE;
	}
}
