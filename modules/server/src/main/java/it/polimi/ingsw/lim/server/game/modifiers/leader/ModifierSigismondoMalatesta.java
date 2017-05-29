package it.polimi.ingsw.lim.server.game.modifiers.leader;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

/**
 * <p>REQUIREMENT:      7 Military Points and 3 Faith Points
 *
 * <p>PERMANENT ABILITY:      Your uncolored Family Member has a bonus of +3 on
 * its value. (You can increase its value by spending servants or if you have
 * Character Cards with this effect.)
 */
public class ModifierSigismondoMalatesta extends Modifier<EventPlaceFamilyMember>
{
	private static final ModifierSigismondoMalatesta INSTANCE = new ModifierSigismondoMalatesta();

	private ModifierSigismondoMalatesta()
	{
		super(EventPlaceFamilyMember.class);
	}

	@Override
	public void apply(EventPlaceFamilyMember event)
	{
		if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
			event.setFamilyMemberValue(event.getFamilyMemberValue() + 3);
		}
	}

	public static ModifierSigismondoMalatesta getInstance()
	{
		return ModifierSigismondoMalatesta.INSTANCE;
	}
}
