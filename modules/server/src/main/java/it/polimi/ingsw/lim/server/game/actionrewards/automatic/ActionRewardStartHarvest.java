package it.polimi.ingsw.lim.server.game.actionrewards.automatic;

import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardAutomatic;
import it.polimi.ingsw.lim.server.game.events.EventStartHarvest;

public class ActionRewardStartHarvest extends ActionRewardAutomatic<EventStartHarvest>
{
	private final int value;

	public ActionRewardStartHarvest(int value)
	{
		this.value = value;
	}

	@Override
	public EventStartHarvest apply()
	{
		return new EventStartHarvest(this.value);
	}

	public int getValue()
	{
		return this.value;
	}
}