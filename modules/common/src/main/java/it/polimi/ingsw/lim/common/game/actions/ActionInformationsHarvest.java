package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationsHarvest extends ActionInformations
{
	private final FamilyMemberType familyMemberType;
	private final int servants;

	public ActionInformationsHarvest(FamilyMemberType familyMemberType, int servants)
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
