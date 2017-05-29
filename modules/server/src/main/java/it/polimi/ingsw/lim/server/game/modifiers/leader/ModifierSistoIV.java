package it.polimi.ingsw.lim.server.game.modifiers.leader;

import it.polimi.ingsw.lim.server.game.events.EventChurchSupport;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

/**
 * <p>REQUIREMENT:       6 wood, 6 stone, 6 coins, and 6 servants
 *
 * <p>PERMANENT ABILITY:       You gain 5 additional Victory Points when you
 * support the Church in a Vatican Report phase.
 */
public class ModifierSistoIV extends Modifier<EventChurchSupport>
{
	private static final ModifierSistoIV INSTANCE = new ModifierSistoIV();

	private ModifierSistoIV()
	{
		super(EventChurchSupport.class);
	}

	@Override
	public void apply(EventChurchSupport event)
	{
		event.setVictoryPoints(event.getVictoryPoints() + 5);
	}

	public static ModifierSistoIV getInstance()
	{
		return ModifierSistoIV.INSTANCE;
	}
}
