package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;

import java.util.List;

public class LeaderCardModifierInformation extends LeaderCardInformation
{
	private final String modifier;

	public LeaderCardModifierInformation(String texturePath, String displayName, String description, List<LeaderCardConditionsOption> conditionsOptions, String modifier)
	{
		super(texturePath, displayName, description, conditionsOptions);
		this.modifier = modifier;
	}

	@Override
	public String getInformation()
	{
		return this.getCommonInformation() + "PERMANENT ABILITY:\n" + this.modifier;
	}
}
