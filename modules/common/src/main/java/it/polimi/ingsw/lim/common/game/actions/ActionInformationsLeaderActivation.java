package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.cards.CardLeader;
import it.polimi.ingsw.lim.common.enums.ActionType;

public class ActionInformationsLeaderActivation extends ActionInformations
{
	private CardLeader cardLeader;

	public ActionInformationsLeaderActivation(CardLeader cardLeader)
	{
		super(ActionType.LEADER_ACTIVATION);
		this.cardLeader = cardLeader;
	}

	public CardLeader getCardLeader()
	{
		return this.cardLeader;
	}
}
