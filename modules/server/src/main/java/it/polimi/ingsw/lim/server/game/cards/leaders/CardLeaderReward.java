package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.server.game.cards.CardLeader;
import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditions;
import it.polimi.ingsw.lim.server.game.utils.Reward;

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
