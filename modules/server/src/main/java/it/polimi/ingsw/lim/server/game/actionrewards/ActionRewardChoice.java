package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.server.enums.ActionRewardType;
import it.polimi.ingsw.lim.server.network.Connection;

public abstract class ActionRewardChoice extends ActionReward
{
	public ActionRewardChoice()
	{
		super(ActionRewardType.CHOICE);
	}

	public abstract void apply(Connection player);
}
