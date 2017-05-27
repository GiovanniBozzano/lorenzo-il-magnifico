package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationsCouncilPalace extends ActionInformations
{
	private FamilyMemberType familyMemberType;
	private int councilPalaceRewardIndex;

	public ActionInformationsCouncilPalace(FamilyMemberType familyMemberType, int councilPalaceRewardIndex)
	{
		super(ActionType.COUNCIL_PALACE);
		this.familyMemberType = familyMemberType;
		this.councilPalaceRewardIndex = councilPalaceRewardIndex;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}

	public int getCouncilPalaceRewardIndex()
	{
		return this.councilPalaceRewardIndex;
	}
}
