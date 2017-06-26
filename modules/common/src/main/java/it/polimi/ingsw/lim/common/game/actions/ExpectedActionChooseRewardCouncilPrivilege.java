package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ExpectedActionChooseRewardCouncilPrivilege extends ExpectedAction
{
	private final int councilPrivilegesNumber;

	public ExpectedActionChooseRewardCouncilPrivilege(int councilPrivilegesNumber)
	{
		super(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
		this.councilPrivilegesNumber = councilPrivilegesNumber;
	}

	public int getCouncilPrivilegesNumber()
	{
		return this.councilPrivilegesNumber;
	}
}
