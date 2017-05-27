package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.server.enums.ActionRewardType;

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
