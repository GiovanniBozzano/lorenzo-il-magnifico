package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;

import java.util.List;

public class LeaderCardModifierInformations extends LeaderCardInformations
{
	private final String modifier;

	public LeaderCardModifierInformations(String texturePath, String displayName, String description, List<LeaderCardConditionsOption> conditionsOptions, String modifier)
	{
		super(texturePath, displayName, description, conditionsOptions);
		this.modifier = modifier;
	}

	@Override
	public String getInformations()
	{
		return this.getCommonInformations() + "PERMANENT ABILITY:\n" + this.modifier;
	}

	public String getModifier()
	{
		return this.modifier;
	}
}
