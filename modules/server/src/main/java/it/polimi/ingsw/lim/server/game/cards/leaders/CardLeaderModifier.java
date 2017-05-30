package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.server.game.cards.CardLeader;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditions;

public class CardLeaderModifier extends CardLeader
{
	private final Modifier modifier;

	public CardLeaderModifier(String displayName, int index, CardLeaderConditions[] conditions, Modifier modifier)
	{
		super(displayName, index, conditions);
		this.modifier = modifier;
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
}
