package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.server.enums.ActionRewardType;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.network.Connection;

public abstract class ActionRewardAutomatic<T extends Event> extends ActionReward
{
	public ActionRewardAutomatic(Connection player)
	{
		super(player, ActionRewardType.AUTOMATIC);
	}

	public abstract T apply();
}
