package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierPlaceFamilyMember extends Modifier<EventPlaceFamilyMember>
{
	private static final ModifierPlaceFamilyMember INSTANCE = new ModifierPlaceFamilyMember();

	private ModifierPlaceFamilyMember()
	{
		super(EventPlaceFamilyMember.class);
	}

	@Override
	public void apply(EventPlaceFamilyMember event)
	{
	}

	public ModifierPlaceFamilyMember getInstance()
	{
		return ModifierPlaceFamilyMember.INSTANCE;
	}
}
