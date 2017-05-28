package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationsProductionTrade extends ActionInformations
{
	private final FamilyMemberType familyMemberType;
	private final int developmentCardBuildingIndex;

	public ActionInformationsProductionTrade(FamilyMemberType familyMemberType, int developmentCardBuildingIndex)
	{
		super(ActionType.PRODUCTION_TRADE);
		this.developmentCardBuildingIndex = developmentCardBuildingIndex;
		this.familyMemberType = familyMemberType;
	}

	public int getDevelopmentCardBuildingIndex()
	{
		return this.developmentCardBuildingIndex;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}
}
