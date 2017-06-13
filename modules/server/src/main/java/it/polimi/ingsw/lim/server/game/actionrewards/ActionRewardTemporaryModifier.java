package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardTemporaryModifier extends ActionReward
{
	public ActionRewardTemporaryModifier()
	{
		super(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER);
	}

	@Override
	public void apply(Connection player)
	{
	}
}
