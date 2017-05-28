package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.server.enums.ActionRewardType;
import it.polimi.ingsw.lim.server.game.events.Event;

public abstract class ActionRewardAutomatic<T extends Event> extends ActionReward
{
	public ActionRewardAutomatic()
	{
		super(ActionRewardType.AUTOMATIC);
	}

	public abstract T apply();
}
