package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformations;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardModifierInformations;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.server.enums.LeaderCardType;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

import java.util.List;

public class LeaderCardModifier extends LeaderCard
{
	private final Modifier<? extends Event> modifier;

	public LeaderCardModifier(int index, String texturePath, String displayName, String description, List<LeaderCardConditionsOption> conditionsOptions, Modifier<? extends Event> modifier)
	{
		super(index, texturePath, displayName, description, LeaderCardType.MODIFIER, conditionsOptions);
		this.modifier = modifier;
	}

	@Override
	public LeaderCardInformations getInformations()
	{
		return new LeaderCardModifierInformations(this.getTexturePath(), this.getDisplayName(), this.getDescription(), this.getConditionsOptions(), this.modifier.getDescription());
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
}
