package it.polimi.ingsw.lim.server.game.modifiers.leader;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;

/**
 * <p>REQUIREMENT: 8 Faith Points
 *
 * <p>PERMANENT ABILITY: Each time you receive wood, stone, coins, or servants
 * as an immediate effect from Development Cards (not from an action space), you
 * receive the resources twice.
 */
public class ModifierSantaRita extends Modifier<EventGainResources>
{
	private static final ModifierSantaRita INSTANCE = new ModifierSantaRita();

	private ModifierSantaRita()
	{
		super(EventGainResources.class);
	}

	@Override
	public void apply(EventGainResources event)
	{
		if (event.getResourcesSource() != ResourcesSource.DEVELOPMENT_CARDS) {
			return;
		}
		for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
			if (resourceAmount.getResourceType() != ResourceType.COIN && resourceAmount.getResourceType() != ResourceType.SERVANT && resourceAmount.getResourceType() != ResourceType.STONE && resourceAmount.getResourceType() != ResourceType.WOOD) {
				continue;
			}
			resourceAmount.setAmount(resourceAmount.getAmount() * 2);
		}
	}

	public static ModifierSantaRita getInstance()
	{
		return ModifierSantaRita.INSTANCE;
	}
}
