package it.polimi.ingsw.lim.server.game.modifiers.leader;

import it.polimi.ingsw.lim.server.game.events.EventGetCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

/**
 * <p>REQUIREMENT: 3 Development Cards, 12 Coins, 2 Faith Points
 *
 * <p>PERMANENT ABILITY: You don’t need to satisfy the Military Points
 * requirement when you take Territory Cards.
 */
public class ModifierCesareBorgia extends Modifier<EventGetCard>
{
	private static final ModifierCesareBorgia INSTANCE = new ModifierCesareBorgia();

	private ModifierCesareBorgia()
	{
		super(EventGetCard.class);
	}

	@Override
	public void apply(EventGetCard event)
	{
		event.setIgnoreSlotLock(true);
	}

	public static ModifierCesareBorgia getInstance()
	{
		return ModifierCesareBorgia.INSTANCE;
	}
}
