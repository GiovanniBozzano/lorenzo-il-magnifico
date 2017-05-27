package it.polimi.ingsw.lim.common.actionrewards.automatic;

import it.polimi.ingsw.lim.common.actionrewards.ActionRewardAutomatic;
import it.polimi.ingsw.lim.common.events.EventStartHarvest;

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