package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

import java.io.Serializable;

public class AvailableActionFamilyMember implements Serializable
{
	private final FamilyMemberType familyMemberType;

	public AvailableActionFamilyMember(FamilyMemberType familyMemberType)
	{
		super();
		this.familyMemberType = familyMemberType;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}
}
