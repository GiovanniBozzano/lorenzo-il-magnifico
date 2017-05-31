package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.server.enums.ActionRewardType;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardHarvest extends ActionReward
{
	private final int value;

	public ActionRewardHarvest(int value)
	{
		super(ActionRewardType.HARVEST);
		this.value = value;
	}

	@Override
	public void apply(Connection player)
	{
	}

	public int getValue()
	{
		return this.value;
	}
}