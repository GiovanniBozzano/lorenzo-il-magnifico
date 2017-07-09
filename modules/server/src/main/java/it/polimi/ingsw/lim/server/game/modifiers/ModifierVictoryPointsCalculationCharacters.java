package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventVictoryPointsCalculation;

public class ModifierVictoryPointsCalculationCharacters extends Modifier<EventVictoryPointsCalculation>
{
	public ModifierVictoryPointsCalculationCharacters(String description)
	{
		super(EventVictoryPointsCalculation.class, description);
	}

	@Override
	public void apply(EventVictoryPointsCalculation event)
	{
		event.setCountingCharacters(false);
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventVictoryPointsCalculation.class);
	}
}
