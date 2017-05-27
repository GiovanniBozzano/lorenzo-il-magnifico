package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.actionrewards.ActionReward;
import it.polimi.ingsw.lim.common.actionrewards.ActionRewardAutomatic;
import it.polimi.ingsw.lim.common.actionrewards.ActionRewardChoice;

public enum ActionRewardType
{
	AUTOMATIC(ActionRewardAutomatic.class),
	CHOICE(ActionRewardChoice.class);
	private Class<? extends ActionReward> actionRewardClass;

	ActionRewardType(Class<? extends ActionReward> actionRewardClass)
	{
		this.actionRewardClass = actionRewardClass;
	}

	public Class<? extends ActionReward> getActionRewardClass()
	{
		return this.actionRewardClass;
	}
}
