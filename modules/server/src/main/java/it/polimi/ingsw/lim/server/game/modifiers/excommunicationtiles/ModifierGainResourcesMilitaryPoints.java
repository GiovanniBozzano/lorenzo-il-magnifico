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
public class ModifierGainResourcesMilitaryPoints extends Modifier<EventGainResources>
{
	private static final ModifierGainResourcesMilitaryPoints INSTANCE = new ModifierGainResourcesMilitaryPoints();

	private ModifierGainResourcesMilitaryPoints()
	{
		super(EventGainResources.class);
	}

	@Override
	public void apply(EventGainResources event)
	{
		for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
			if (resourceAmount.getResourceType() == ResourceType.MILITARY_POINT) {
				resourceAmount.setAmount(resourceAmount.getAmount() - event.getSourcesCount());
				return;
			}
		}
	}

	public ModifierGainResourcesMilitaryPoints getInstance()
	{
		return ModifierGainResourcesMilitaryPoints.INSTANCE;
	}
}