package it.polimi.ingsw.lim.server.game.actionrewards.automatic;

import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardAutomatic;
import it.polimi.ingsw.lim.server.game.events.EventStartHarvest;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardHarvest extends ActionRewardAutomatic<EventStartHarvest>
{
	private final int value;

	public ActionRewardHarvest(int value)
	{
		this.value = value;
	}

	@Override
	public EventStartHarvest apply(Connection player)
	{
		return new EventStartHarvest(player, this.value);
	}

	public int getValue()
	{
		return this.value;
	}
}