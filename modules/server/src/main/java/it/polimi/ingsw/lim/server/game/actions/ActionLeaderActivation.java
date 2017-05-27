package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.cards.CardLeader;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderActivation;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionLeaderActivation extends ActionInformationsLeaderActivation implements IAction
{
	private Connection player;

	public ActionLeaderActivation(Connection player, CardLeader cardLeader)
	{
		super(cardLeader);
		this.player = player;
	}

	@Override
	public boolean isLegal()
	{
		return false;
	}

	@Override
	public void apply()
	{
	}

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}
}
