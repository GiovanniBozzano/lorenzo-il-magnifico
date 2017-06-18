package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionChooseLorenzoDeMediciLeader extends ActionInformationsChooseLorenzoDeMediciLeader implements IAction
{
	private final Connection player;

	public ActionChooseLorenzoDeMediciLeader(int leaderCardIndex, Connection player)
	{
		super(leaderCardIndex);
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
}
