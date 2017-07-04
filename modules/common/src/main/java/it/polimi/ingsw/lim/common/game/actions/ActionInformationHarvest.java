package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationHarvest extends ActionInformation
{
	private final FamilyMemberType familyMemberType;
	private final int servants;

	public ActionInformationHarvest(FamilyMemberType familyMemberType, int servants)
	{
		super(ActionType.HARVEST);
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
