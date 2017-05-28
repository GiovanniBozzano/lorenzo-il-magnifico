package it.polimi.ingsw.lim.server.game.actionrewards.automatic;

import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardAutomatic;
import it.polimi.ingsw.lim.server.game.events.EventStartProduction;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardStartProduction extends ActionRewardAutomatic<EventStartProduction>
{
	private final int value;

	public ActionRewardStartProduction(Connection player, int value)
	{
		super(player);
		this.value = value;
	}

	@Override
	public EventStartProduction apply()
	{
		return new EventStartProduction(this.getPlayer(), this.value);
	}

	public int getValue()
	{
		return this.value;
	}
}