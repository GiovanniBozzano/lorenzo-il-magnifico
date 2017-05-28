package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsLeaderActivation extends ActionInformations
{
	private final int cardLeaderIndex;

	public ActionInformationsLeaderActivation(int cardLeaderIndex)
	{
		super(ActionType.LEADER_ACTIVATION);
		this.cardLeaderIndex = cardLeaderIndex;
	}

	public int getCardLeaderIndex()
	{
		return this.cardLeaderIndex;
	}
}
