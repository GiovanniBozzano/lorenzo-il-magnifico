package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationsProduction extends ActionInformations
{
	private FamilyMemberType familyMemberType;

	public ActionInformationsProduction(FamilyMemberType familyMemberType)
	{
		super(ActionType.PRODUCTION);
		this.familyMemberType = familyMemberType;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}
}
