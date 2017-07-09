package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;

public class ModifierPlaceFamilyMemberValue extends Modifier<EventPlaceFamilyMember>
{
	private final int value;

	public ModifierPlaceFamilyMemberValue(String description, int value)
	{
		super(EventPlaceFamilyMember.class, description);
		this.value = value;
	}

	@Override
	public void apply(EventPlaceFamilyMember event)
	{
		event.setFamilyMemberValue(event.getFamilyMemberValue() + this.value);
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventPlaceFamilyMember.class);
	}
}
