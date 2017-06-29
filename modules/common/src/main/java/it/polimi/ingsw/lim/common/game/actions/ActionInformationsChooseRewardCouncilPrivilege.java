package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.util.ArrayList;
import java.util.List;

public class ActionInformationsChooseRewardCouncilPrivilege extends ActionInformations
{
	private final List<Integer> councilPalaceRewardIndexes;

	public ActionInformationsChooseRewardCouncilPrivilege(List<Integer> councilPalaceRewardIndexes)
	{
		super(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
		this.councilPalaceRewardIndexes = new ArrayList<>(councilPalaceRewardIndexes);
	}

	public List<Integer> getCouncilPrivilegeRewardIndexes()
	{
		return this.councilPalaceRewardIndexes;
	}
}
