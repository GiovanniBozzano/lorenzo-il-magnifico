package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.server.game.events.EventPostVictoryPointsCalculation;

public class ModifierPostVictoryPointsCalculationBuildings extends Modifier<EventPostVictoryPointsCalculation>
{
	public ModifierPostVictoryPointsCalculationBuildings(String description)
	{
		super(EventPostVictoryPointsCalculation.class, description);
	}

	@Override
	public void apply(EventPostVictoryPointsCalculation event)
	{
		for (DevelopmentCardBuilding developmentCardBuilding : event.getPlayer().getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
			for (ResourceCostOption resourceCostOption : developmentCardBuilding.getResourceCostOptions()) {
				for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
					if (resourceAmount.getResourceType() == ResourceType.STONE || resourceAmount.getResourceType() == ResourceType.WOOD) {
						event.setVictoryPoints(event.getVictoryPoints() - resourceAmount.getAmount());
					}
				}
			}
		}
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventPostVictoryPointsCalculation.class);
	}
}
