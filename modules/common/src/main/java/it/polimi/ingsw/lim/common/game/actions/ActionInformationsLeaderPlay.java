package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;

public class ActionInformationsLeaderPlay extends ActionInformations
{
	private final int leaderCardIndex;
	private final LeaderCardConditionsOption leaderCardConditionsOption;

	public ActionInformationsLeaderPlay(int leaderCardIndex, LeaderCardConditionsOption leaderCardConditionsOption)
	{
		super(ActionType.LEADER_PLAY);
		this.leaderCardIndex = leaderCardIndex;
		this.leaderCardConditionsOption = leaderCardConditionsOption;
	}

	public int getLeaderCardIndex()
	{
		return this.leaderCardIndex;
	}

	public LeaderCardConditionsOption getLeaderCardConditionsOption()
	{
		return this.leaderCardConditionsOption;
	}
}
