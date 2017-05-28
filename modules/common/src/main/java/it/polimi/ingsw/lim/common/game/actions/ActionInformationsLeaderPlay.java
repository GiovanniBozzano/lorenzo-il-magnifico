package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsLeaderPlay extends ActionInformations
{
	private final int cardLeaderIndex;

	public ActionInformationsLeaderPlay(int cardLeaderIndex)
	{
		super(ActionType.LEADER_PLAY);
		this.cardLeaderIndex = cardLeaderIndex;
	}

	public int getCardLeaderIndex()
	{
		return this.cardLeaderIndex;
	}
}
