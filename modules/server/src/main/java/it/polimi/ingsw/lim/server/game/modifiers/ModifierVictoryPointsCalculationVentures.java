package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventVictoryPointsCalculation;

public class ModifierVictoryPointsCalculationVentures extends Modifier<EventVictoryPointsCalculation>
{
	public ModifierVictoryPointsCalculationVentures(String description)
	{
		super(EventVictoryPointsCalculation.class, description);
	}

	@Override
	public void apply(EventVictoryPointsCalculation event)
	{
		event.setCountingVentures(false);
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventVictoryPointsCalculation.class);
	}
}
