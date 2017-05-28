package it.polimi.ingsw.lim.server.game.actionrewards.automatic;

import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardAutomatic;
import it.polimi.ingsw.lim.server.game.events.EventStartHarvest;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardStartHarvest extends ActionRewardAutomatic<EventStartHarvest>
{
	private final int value;

	public ActionRewardStartHarvest(Connection player, int value)
	{
		super(player);
		this.value = value;
	}

	@Override
	public EventStartHarvest apply()
	{
		return new EventStartHarvest(this.getPlayer(), this.value);
	}

	public int getValue()
	{
		return this.value;
	}
}