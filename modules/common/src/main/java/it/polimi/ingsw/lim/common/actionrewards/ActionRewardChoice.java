package it.polimi.ingsw.lim.common.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionRewardType;

public abstract class ActionRewardChoice extends ActionReward
{
	public ActionRewardChoice()
	{
		super(ActionRewardType.CHOICE);
	}

	public abstract void apply();
}
