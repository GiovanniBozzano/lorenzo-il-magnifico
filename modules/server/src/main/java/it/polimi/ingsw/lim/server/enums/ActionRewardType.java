package it.polimi.ingsw.lim.server.enums;

import it.polimi.ingsw.lim.server.game.actionrewards.ActionReward;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardAutomatic;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardChoice;

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
