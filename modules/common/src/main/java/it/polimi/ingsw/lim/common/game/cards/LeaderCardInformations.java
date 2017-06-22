package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;

import java.util.ArrayList;
import java.util.List;

public abstract class LeaderCardInformations extends CardInformations
{
	private final List<LeaderCardConditionsOption> conditionsOptions;
	private final String description;

	public LeaderCardInformations(String texturePath, String displayName, String description, List<LeaderCardConditionsOption> conditionsOptions)
	{
		super(texturePath, displayName);
		this.conditionsOptions = new ArrayList<>(conditionsOptions);
		this.description = description;
	}

	public List<LeaderCardConditionsOption> getConditionsOptions()
	{
		return this.conditionsOptions;
	}

	public String getDescription()
	{
		return this.description;
	}
}
