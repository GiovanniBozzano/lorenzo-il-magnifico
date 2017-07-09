package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.events.EventPostVictoryPointsCalculation;

import java.util.ArrayList;
import java.util.List;

public class ModifierPostVictoryPointsCalculationResources extends Modifier<EventPostVictoryPointsCalculation>
{
	private final List<ResourceType> resourceTypes;

	public ModifierPostVictoryPointsCalculationResources(String description, List<ResourceType> resourceTypes)
	{
		super(EventPostVictoryPointsCalculation.class, description);
		this.resourceTypes = new ArrayList<>(resourceTypes);
	}

	@Override
	public void apply(EventPostVictoryPointsCalculation event)
	{
		this.resourceTypes.forEach(resourceType -> event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerResourceHandler().getResources().get(resourceType)));
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventPostVictoryPointsCalculation.class);
	}
}
