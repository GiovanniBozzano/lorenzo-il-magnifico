package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class AvailableActionFamilyMember extends AvailableAction
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
