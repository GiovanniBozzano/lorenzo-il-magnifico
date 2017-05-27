package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.cards.CardLeader;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderDiscard;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionLeaderDiscard extends ActionInformationsLeaderDiscard implements IAction
{
	private Connection player;

	public ActionLeaderDiscard(Connection player, CardLeader cardLeader)
	{
		super(cardLeader);
		this.player = player;
	}

	@Override
	public void isLegal()
	{
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
