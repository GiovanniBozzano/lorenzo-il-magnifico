package it.polimi.ingsw.lim.common.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionRewardType;
import it.polimi.ingsw.lim.common.events.IEvent;

public abstract class ActionRewardAutomatic<T extends IEvent> extends ActionReward
{
	public ActionRewardAutomatic()
	{
		super(ActionRewardType.AUTOMATIC);
	}

	public abstract T apply();
}
