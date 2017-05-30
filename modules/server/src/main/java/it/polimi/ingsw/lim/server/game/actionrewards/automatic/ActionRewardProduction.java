package it.polimi.ingsw.lim.server.game.actionrewards.automatic;

import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardAutomatic;
import it.polimi.ingsw.lim.server.game.events.EventStartProduction;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardProduction extends ActionRewardAutomatic<EventStartProduction>
{
	private final int value;

	public ActionRewardProduction(int value)
	{
		this.value = value;
	}

	@Override
	public EventStartProduction apply(Connection player)
	{
		return new EventStartProduction(player, this.value);
	}

	public int getValue()
	{
		return this.value;
	}
}