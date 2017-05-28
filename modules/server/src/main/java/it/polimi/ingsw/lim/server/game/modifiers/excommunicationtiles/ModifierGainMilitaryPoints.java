package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;

/**
 * <p>Each time you gain Military Points (from action spaces or from your
 * Cards), gain 1 fewer Military Point. (If you have more Cards that give you
 * Military Points, consider each Card a single source, so you gain -1 Military
 * Point for each card).
 */
public class ModifierGainMilitaryPoints extends Modifier<EventGainResources>
{
	private static final ModifierGainMilitaryPoints INSTANCE = new ModifierGainMilitaryPoints();

	private ModifierGainMilitaryPoints()
	{
		super(EventGainResources.class);
	}

	@Override
	public EventGainResources apply(EventGainResources event)
	{
		for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
			if (resourceAmount.getResourceType() == ResourceType.MILITARY_POINT) {
				resourceAmount.setAmount(resourceAmount.getAmount() - event.getSourcesCount());
				return event;
			}
		}
		return event;
	}

	public ModifierGainMilitaryPoints getInstance()
	{
		return ModifierGainMilitaryPoints.INSTANCE;
	}
}