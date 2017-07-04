package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationCouncilPalace extends ActionInformation
{
	private final FamilyMemberType familyMemberType;
	private final int servants;

	public ActionInformationCouncilPalace(FamilyMemberType familyMemberType, int servants)
	{
		super(ActionType.COUNCIL_PALACE);
		this.familyMemberType = familyMemberType;
		this.servants = servants;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}

	public int getServants()
	{
		return this.servants;
	}
}
