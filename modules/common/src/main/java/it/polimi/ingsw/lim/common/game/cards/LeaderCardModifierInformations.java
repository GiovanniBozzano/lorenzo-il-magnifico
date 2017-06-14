package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;

import java.util.List;

public class LeaderCardModifierInformations extends LeaderCardInformations
{
	private final String modifier;

	public LeaderCardModifierInformations(int index, String texturePath, String description, List<LeaderCardConditionsOption> conditionsOptions, String modifier)
	{
		super(index, texturePath, description, conditionsOptions);
		this.modifier = modifier;
	}

	public String getModifier()
	{
		return this.modifier;
	}
}
