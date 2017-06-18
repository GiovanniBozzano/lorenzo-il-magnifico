package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsChooseLorenzoDeMediciLeader extends ActionInformations
{
	private final int leaderCardIndex;

	public ActionInformationsChooseLorenzoDeMediciLeader(int leaderCardIndex)
	{
		super(ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER);
		this.leaderCardIndex = leaderCardIndex;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}
}
