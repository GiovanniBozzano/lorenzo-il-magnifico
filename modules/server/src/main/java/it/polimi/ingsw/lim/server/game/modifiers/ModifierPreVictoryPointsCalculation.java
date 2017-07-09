package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventPreVictoryPointsCalculation;

public class ModifierPreVictoryPointsCalculation extends Modifier<EventPreVictoryPointsCalculation>
{
	public ModifierPreVictoryPointsCalculation(String description)
	{
		super(EventPreVictoryPointsCalculation.class, description);
	}

	@Override
	public void apply(EventPreVictoryPointsCalculation event)
	{
		event.setVictoryPoints(event.getVictoryPoints() - event.getVictoryPoints() / 5);
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventPreVictoryPointsCalculation.class);
	}
}
