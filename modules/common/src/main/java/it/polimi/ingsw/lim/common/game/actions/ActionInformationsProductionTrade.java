package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationsProductionTrade extends ActionInformations
{
	private FamilyMemberType familyMemberType;
	private DevelopmentCardBuilding developmentCardBuilding;

	public ActionInformationsProductionTrade(FamilyMemberType familyMemberType, DevelopmentCardBuilding developmentCardBuilding)
	{
		super(ActionType.PRODUCTION_TRADE);
		this.developmentCardBuilding = developmentCardBuilding;
		this.familyMemberType = familyMemberType;
	}

	public DevelopmentCardBuilding getDevelopmentCardBuilding()
	{
		return this.developmentCardBuilding;
	}

	public FamilyMemberType getFamilyMemberType() {
		return this.familyMemberType;
	}
}
