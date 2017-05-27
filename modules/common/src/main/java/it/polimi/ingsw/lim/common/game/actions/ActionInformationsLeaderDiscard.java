package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.cards.CardLeader;
import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsLeaderDiscard extends ActionInformations
{
	private CardLeader cardLeader;

	public ActionInformationsLeaderDiscard(CardLeader cardLeader)
	{
		super(ActionType.LEADER_DISCARD);
		this.cardLeader = cardLeader;
	}

	public CardLeader getCardLeader()
	{
		return this.cardLeader;
	}
}
