package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.server.game.bonus.Bonus;
import it.polimi.ingsw.lim.server.game.cards.CardLeader;
import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditions;

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
