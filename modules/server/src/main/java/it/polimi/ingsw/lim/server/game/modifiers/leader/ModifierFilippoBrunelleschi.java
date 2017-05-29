package it.polimi.ingsw.lim.server.game.modifiers.leader;

import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

/**
 * <p>REQUIREMENT:  5 Building Cards
 *
 * <p>PERMANENT ABILITY:   You don’t have to spend 3 coins when you place your
 * Family Members in a Tower that is already occupied.
 */
public class ModifierFilippoBrunelleschi extends Modifier<EventPlaceFamilyMember>
{
	private static final ModifierFilippoBrunelleschi INSTANCE = new ModifierFilippoBrunelleschi();

	private ModifierFilippoBrunelleschi()
	{
		super(EventPlaceFamilyMember.class);
	}

	@Override
	public void apply(EventPlaceFamilyMember event)
	{
		event.setIgnoreOccupiedTax(true);
	}

	public static ModifierFilippoBrunelleschi getInstance()
	{
		return ModifierFilippoBrunelleschi.INSTANCE;
	}
}
