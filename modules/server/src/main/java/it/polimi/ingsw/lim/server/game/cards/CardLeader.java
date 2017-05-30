package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditions;

public abstract class CardLeader extends Card
{
	private final CardLeaderConditions[] conditions;

	public CardLeader(String displayName, int index, CardLeaderConditions[] conditions)
	{
		super(displayName, index);
		this.conditions = conditions;
	}

	public CardLeaderConditions[] getConditions()
	{
		return this.conditions;
	}
}
