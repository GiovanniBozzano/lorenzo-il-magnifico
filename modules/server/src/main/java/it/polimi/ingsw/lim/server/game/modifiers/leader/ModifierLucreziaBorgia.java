package it.polimi.ingsw.lim.server.game.modifiers.leader;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

/**
 * <p>REQUIREMENT: 6 Development Cards of the same type
 *
 * <p>PERMANENT ABILITY: Your colored Family Members have a bonus of +2 on their
 * value. (You can increase their value by spending servants or if you have
 * Character Cards with this effect).
 */
public class ModifierLucreziaBorgia extends Modifier<EventPlaceFamilyMember>
{
	private static final ModifierLucreziaBorgia INSTANCE = new ModifierLucreziaBorgia();

	private ModifierLucreziaBorgia()
	{
		super(EventPlaceFamilyMember.class);
	}

	@Override
	public void apply(EventPlaceFamilyMember event)
	{
		if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
			return;
		}
		event.setFamilyMemberValue(event.getFamilyMemberValue() + 2);
	}

	public static ModifierLucreziaBorgia getInstance()
	{
		return ModifierLucreziaBorgia.INSTANCE;
	}
}
