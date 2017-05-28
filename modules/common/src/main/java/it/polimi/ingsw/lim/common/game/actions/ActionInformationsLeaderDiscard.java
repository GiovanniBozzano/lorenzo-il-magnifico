package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsLeaderDiscard extends ActionInformations
{
	private final int cardLeaderIndex;

	public ActionInformationsLeaderDiscard(int cardLeaderIndex)
	{
		super(ActionType.LEADER_DISCARD);
		this.cardLeaderIndex = cardLeaderIndex;
	}

	public int getCardLeaderIndex()
	{
		return this.cardLeaderIndex;
	}
}
