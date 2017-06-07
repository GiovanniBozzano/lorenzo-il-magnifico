package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.server.game.cards.CardLeader;
import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditionsOption;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class CardLeaderReward extends CardLeader
{
	private final Reward reward;

	public CardLeaderReward(String displayName, int index, List<CardLeaderConditionsOption> conditionsOptions, Reward reward)
	{
		super(displayName, index, conditionsOptions);
		this.reward = reward;
	}

	public Reward getReward()
	{
		return this.reward;
	}
}
