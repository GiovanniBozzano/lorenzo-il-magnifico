package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationsHarvestStart extends ActionInformations
{
	private final FamilyMemberType familyMemberType;

	public ActionInformationsHarvestStart(FamilyMemberType familyMemberType)
	{
		super(ActionType.HARVEST_START);
		this.familyMemberType = familyMemberType;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}
}
