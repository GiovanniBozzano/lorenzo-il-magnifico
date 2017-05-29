package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierPlaceFamilyMemberMarket extends Modifier<EventPlaceFamilyMember>
{
	private static final ModifierPlaceFamilyMemberMarket INSTANCE = new ModifierPlaceFamilyMemberMarket();

	private ModifierPlaceFamilyMemberMarket()
	{
		super(EventPlaceFamilyMember.class);
	}

	@Override
	public void apply(EventPlaceFamilyMember event)
	{
	}

	public ModifierPlaceFamilyMemberMarket getInstance()
	{
		return ModifierPlaceFamilyMemberMarket.INSTANCE;
	}
}
