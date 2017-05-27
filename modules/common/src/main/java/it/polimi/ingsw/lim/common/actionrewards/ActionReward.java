package it.polimi.ingsw.lim.common.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionRewardType;

public class ActionReward
{
	private ActionRewardType actionRewardType;

	public ActionReward(ActionRewardType actionRewardType)
	{
		this.actionRewardType = actionRewardType;
	}

	public ActionRewardType getActionRewardType()
	{
		return this.actionRewardType;
	}
}
