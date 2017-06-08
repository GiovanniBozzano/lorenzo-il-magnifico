package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardTemporaryModifier extends ActionReward
{
	private Modifier<? extends Event> modifier;

	public ActionRewardTemporaryModifier()
	{
		super(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER);
	}

	public void apply(Connection player)
	{
	}

	public Modifier<? extends Event> getModifier()
	{
		return this.modifier;
	}
}
