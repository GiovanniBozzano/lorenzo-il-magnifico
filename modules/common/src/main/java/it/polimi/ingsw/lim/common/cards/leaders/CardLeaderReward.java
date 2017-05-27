package it.polimi.ingsw.lim.common.cards.leaders;

import it.polimi.ingsw.lim.common.cards.CardLeader;
import it.polimi.ingsw.lim.common.game.CardLeaderConditions;
import it.polimi.ingsw.lim.common.game.Reward;

public abstract class CardLeaderReward extends CardLeader
{
	private final Reward reward;

	public CardLeaderReward(String displayName, CardLeaderConditions[] conditions, Reward reward)
	{
		super(displayName, conditions);
		this.reward = reward;
	}

	public Reward getReward()
	{
		return this.reward;
	}
}
