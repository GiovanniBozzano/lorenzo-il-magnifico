package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.LeaderCardConditionsOption;

import java.util.List;

public class LeaderCardModifier extends LeaderCard
{
	private final Modifier<? extends Event> modifier;

	public LeaderCardModifier(String displayName, int index, List<LeaderCardConditionsOption> conditionsOptions, String description, Modifier<? extends Event> modifier)
	{
		super(displayName, index, conditionsOptions, description);
		this.modifier = modifier;
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
}
