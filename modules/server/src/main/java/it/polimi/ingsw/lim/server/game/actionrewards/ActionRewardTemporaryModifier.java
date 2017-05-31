package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.server.enums.ActionRewardType;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionRewardTemporaryModifier extends ActionReward
{
	private Modifier<? extends Event> modifier;

	public ActionRewardTemporaryModifier()
	{
		super(ActionRewardType.TEMPORARY_MODIFIER);
	}

	public void apply(Connection player)
	{
	}

	public Modifier<? extends Event> getModifier()
	{
		return this.modifier;
	}
}
