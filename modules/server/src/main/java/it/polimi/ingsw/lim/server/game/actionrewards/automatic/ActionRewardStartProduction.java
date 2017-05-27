package it.polimi.ingsw.lim.server.game.actionrewards.automatic;

import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardAutomatic;
import it.polimi.ingsw.lim.server.game.events.EventStartProduction;

public class ActionRewardStartProduction extends ActionRewardAutomatic<EventStartProduction>
{
	private final int value;

	public ActionRewardStartProduction(int value)
	{
		this.value = value;
	}

	@Override
	public EventStartProduction apply()
	{
		return new EventStartProduction(this.value);
	}

	public int getValue()
	{
		return this.value;
	}
}