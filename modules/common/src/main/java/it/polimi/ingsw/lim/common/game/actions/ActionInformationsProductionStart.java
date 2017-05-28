package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationsProductionStart extends ActionInformations
{
	private final FamilyMemberType familyMemberType;

	public ActionInformationsProductionStart(FamilyMemberType familyMemberType)
	{
		super(ActionType.PRODUCTION_START);
		this.familyMemberType = familyMemberType;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}
}
