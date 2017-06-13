package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.server.game.cards.CardLeader;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditionsOption;

import java.util.List;

public class CardLeaderModifier extends CardLeader
{
	private final Modifier<? extends Event> modifier;

	public CardLeaderModifier(String displayName, int index, List<CardLeaderConditionsOption> conditionsOptions, Modifier<? extends Event> modifier)
	{
		super(displayName, index, conditionsOptions);
		this.modifier = modifier;
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
}
