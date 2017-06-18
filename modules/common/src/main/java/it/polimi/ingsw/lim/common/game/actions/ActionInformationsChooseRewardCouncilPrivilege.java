package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.util.ArrayList;
import java.util.List;

public class ActionInformationsChooseRewardCouncilPrivilege extends ActionInformations
{
	private final List<List<Integer>> councilPalaceRewardIndexes;

	public ActionInformationsChooseRewardCouncilPrivilege(List<List<Integer>> councilPalaceRewardIndexes)
	{
		super(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
		this.councilPalaceRewardIndexes = new ArrayList<>(councilPalaceRewardIndexes);
	}

	public List<List<Integer>> getCouncilPalaceRewardIndexes()
	{
		return this.councilPalaceRewardIndexes;
	}
}
