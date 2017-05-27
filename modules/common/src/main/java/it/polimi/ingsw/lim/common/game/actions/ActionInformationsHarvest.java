package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationsHarvest extends ActionInformations
{
	private FamilyMemberType familyMemberType;

	public ActionInformationsHarvest(FamilyMemberType familyMemberType)
	{
		super(ActionType.HARVEST);
		this.familyMemberType = familyMemberType;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}
}
