package it.polimi.ingsw.lim.common.cards.leaders;

import it.polimi.ingsw.lim.common.bonus.Bonus;
import it.polimi.ingsw.lim.common.cards.CardLeader;
import it.polimi.ingsw.lim.common.game.CardLeaderConditions;

public abstract class CardLeaderBonus extends CardLeader
{
	private final Bonus bonus;

	public CardLeaderBonus(String displayName, CardLeaderConditions[] conditions, Bonus bonus)
	{
		super(displayName, conditions);
		this.bonus = bonus;
	}

	public Bonus getBonus()
	{
		return this.bonus;
	}
}
