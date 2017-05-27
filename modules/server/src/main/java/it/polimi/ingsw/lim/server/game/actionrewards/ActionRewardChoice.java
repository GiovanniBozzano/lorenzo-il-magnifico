package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.server.enums.ActionRewardType;

public abstract class ActionRewardChoice extends ActionReward
{
	public ActionRewardChoice()
	{
		super(ActionRewardType.CHOICE);
	}

	public abstract void apply();
}
