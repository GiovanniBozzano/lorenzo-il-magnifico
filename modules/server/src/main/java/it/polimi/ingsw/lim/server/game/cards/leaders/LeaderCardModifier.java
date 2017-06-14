package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.server.enums.LeaderCardType;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

import java.util.List;

public class LeaderCardModifier extends LeaderCard
{
	private final Modifier<? extends Event> modifier;

	public LeaderCardModifier(int index, String texturePath, String displayName, List<LeaderCardConditionsOption> conditionsOptions, String description, Modifier<? extends Event> modifier)
	{
		super(index, texturePath, displayName, LeaderCardType.MODIFIER, conditionsOptions, description);
		this.modifier = modifier;
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
}
