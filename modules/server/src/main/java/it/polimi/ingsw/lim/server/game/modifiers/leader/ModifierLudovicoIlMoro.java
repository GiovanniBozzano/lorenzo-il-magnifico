package it.polimi.ingsw.lim.server.game.modifiers.leader;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

/**
 * <p>REQUIREMENT:     2 Territory Cards, 2 Character Cards, 2 Building Cards,
 * and 2 Venture Cards
 *
 * <p>PERMANENT ABILITY:     Your colored Family Members has a value of 5,
 * regardless of their related dice. (You can increase their value by spending
 * servants or if you have Character Cards with this effect.)
 */
public class ModifierLudovicoIlMoro extends Modifier<EventPlaceFamilyMember>
{
	private static final ModifierLudovicoIlMoro INSTANCE = new ModifierLudovicoIlMoro();

	private ModifierLudovicoIlMoro()
	{
		super(EventPlaceFamilyMember.class);
	}

	@Override
	public void apply(EventPlaceFamilyMember event)
	{
		if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
			return;
		}
		event.setFamilyMemberValue(5);
	}

	public static ModifierLudovicoIlMoro getInstance()
	{
		return ModifierLudovicoIlMoro.INSTANCE;
	}
}
