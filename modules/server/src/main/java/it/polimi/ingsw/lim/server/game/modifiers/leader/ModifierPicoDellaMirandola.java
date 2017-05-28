package it.polimi.ingsw.lim.server.game.modifiers.leader;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.events.EventGetCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;

/**
 * <p>REQUIREMENT: 4 Venture Cards and 2 Building Cards
 *
 * <p>PERMANENT ABILITY:
 * When you take Development Cards, you get a discount of 3 coins (if the
 * card you are taking has coins in its cost.) This is not a discount on the
 * coins you must spend if you take a Development Card from a Tower that’s
 * already occupied.
 */
public class ModifierPicoDellaMirandola extends Modifier<EventGetCard>
{
	private static final ModifierPicoDellaMirandola INSTANCE = new ModifierPicoDellaMirandola();

	private ModifierPicoDellaMirandola()
	{
		super(EventGetCard.class);
	}

	@Override
	public EventGetCard apply(EventGetCard event)
	{
		for (ResourceAmount resourceAmount : event.getCost()) {
			if (resourceAmount.getResourceType() == ResourceType.COIN) {
				resourceAmount.setAmount(resourceAmount.getAmount() - 3);
				return event;
			}
		}
		return event;
	}

	public static ModifierPicoDellaMirandola getInstance()
	{
		return ModifierPicoDellaMirandola.INSTANCE;
	}
}
