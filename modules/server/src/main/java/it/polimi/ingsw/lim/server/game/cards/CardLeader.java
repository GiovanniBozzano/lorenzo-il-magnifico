package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditionsOption;

public abstract class CardLeader extends Card
{
	private final CardLeaderConditionsOption[] conditionsOptions;

	public CardLeader(String displayName, int index, CardLeaderConditionsOption[] conditionsOptions)
	{
		super(displayName, index);
		this.conditionsOptions = conditionsOptions;
	}

	public CardLeaderConditionsOption[] getConditionsOptions()
	{
		return this.conditionsOptions;
	}
}
