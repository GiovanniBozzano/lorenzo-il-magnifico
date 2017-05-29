package it.polimi.ingsw.lim.server.game.modifiers.leader;

import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

/**
 * <p>REQUIREMENT: 5 Character Cards
 *
 * <p>PERMANENT ABILITY: You can place your Family Members in occupied action
 * spaces
 */
public class ModifierLudovicoAriosto extends Modifier<EventPlaceFamilyMember>
{
	private static final ModifierLudovicoAriosto INSTANCE = new ModifierLudovicoAriosto();

	private ModifierLudovicoAriosto()
	{
		super(EventPlaceFamilyMember.class);
	}

	@Override
	public void apply(EventPlaceFamilyMember event)
	{
		event.setIgnoreOccupied(true);
	}

	public static ModifierLudovicoAriosto getInstance()
	{
		return ModifierLudovicoAriosto.INSTANCE;
	}
}
