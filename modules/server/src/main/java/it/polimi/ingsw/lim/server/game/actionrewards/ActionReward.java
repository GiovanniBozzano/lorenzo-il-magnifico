package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.server.enums.ActionRewardType;
import it.polimi.ingsw.lim.server.network.Connection;

public abstract class ActionReward
{
	private final ActionRewardType actionRewardType;

	public ActionReward(ActionRewardType actionRewardType)
	{
		this.actionRewardType = actionRewardType;
	}

	public abstract void apply(Connection player);

	public ActionRewardType getActionRewardType()
	{
		return this.actionRewardType;
	}
}
