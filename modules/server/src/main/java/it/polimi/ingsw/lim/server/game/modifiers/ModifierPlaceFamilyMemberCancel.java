package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;

import java.util.ArrayList;
import java.util.List;

public class ModifierPlaceFamilyMemberCancel extends Modifier<EventPlaceFamilyMember>
{
	private final List<BoardPosition> boardPositions;

	public ModifierPlaceFamilyMemberCancel(String description, List<BoardPosition> boardPositions)
	{
		super(EventPlaceFamilyMember.class, description);
		this.boardPositions = new ArrayList<>(boardPositions);
	}

	@Override
	public void apply(EventPlaceFamilyMember event)
	{
		if (this.boardPositions.contains(event.getBoardPosition())) {
			event.setCancelled(true);
		}
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventPlaceFamilyMember.class);
	}
}
