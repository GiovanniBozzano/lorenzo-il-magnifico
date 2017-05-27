package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.CouncilPalaceReward;

public class ActionInformationsCouncilPalace extends ActionInformations
{
	private FamilyMemberType familyMemberType;
	private CouncilPalaceReward councilPalaceReward;

	public ActionInformationsCouncilPalace(FamilyMemberType familyMemberType, CouncilPalaceReward councilPalaceReward)
	{
		super(ActionType.COUNCIL_PALACE);
		this.familyMemberType = familyMemberType;
		this.councilPalaceReward = councilPalaceReward;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}

	public CouncilPalaceReward getCouncilPalaceReward()
	{
		return this.councilPalaceReward;
	}
}
