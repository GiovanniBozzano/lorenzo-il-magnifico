package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ActionInformationsCouncilPalace extends ActionInformations
{
	private final FamilyMemberType familyMemberType;
	private final int councilPalaceRewardIndex;

	public ActionInformationsCouncilPalace(FamilyMemberType familyMemberType, int councilPalaceRewardIndex)
	{
		super(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
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
