package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.network.Connection;

public class EventPlaceFamilyMember extends Event
{
	private final FamilyMemberType familyMemberType;
	private final BoardPosition boardPosition;
	private int familyMemberValue;
	private boolean ignoreOccupied = false;
	private boolean ignoreOccupiedTax = false;
	private boolean cancelled = false;

	public EventPlaceFamilyMember(Connection player, FamilyMemberType familyMemberType, BoardPosition boardPosition, int familyMemberValue)
	{
		super(player);
		this.familyMemberType = familyMemberType;
		this.boardPosition = boardPosition;
		this.familyMemberValue = familyMemberValue;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}

	public BoardPosition getBoardPosition()
	{
		return this.boardPosition;
	}

	public int getFamilyMemberValue()
	{
		return this.familyMemberValue;
	}

	public void setFamilyMemberValue(int familyMemberValue)
	{
		this.familyMemberValue = familyMemberValue <= 0 ? 0 : familyMemberValue;
	}

	public boolean isIgnoreOccupied()
	{
		return this.ignoreOccupied;
	}

	public void setIgnoreOccupied(boolean ignoreOccupied)
	{
		this.ignoreOccupied = ignoreOccupied;
	}

	public boolean isIgnoreOccupiedTax()
	{
		return this.ignoreOccupiedTax;
	}

	public void setIgnoreOccupiedTax(boolean ignoreOccupiedTax)
	{
		this.ignoreOccupiedTax = ignoreOccupiedTax;
	}

	public boolean isCancelled()
	{
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}
}
