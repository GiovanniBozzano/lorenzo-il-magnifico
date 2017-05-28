package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.server.enums.ActionRewardType;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionReward
{
	private final ActionRewardType actionRewardType;
	private final Connection player;

	public ActionReward(Connection player, ActionRewardType actionRewardType)
	{
		this.player = player;
		this.actionRewardType = actionRewardType;
	}

	public Connection getPlayer()
	{
		return this.player;
	}

	public ActionRewardType getActionRewardType()
	{
		return this.actionRewardType;
	}
}
