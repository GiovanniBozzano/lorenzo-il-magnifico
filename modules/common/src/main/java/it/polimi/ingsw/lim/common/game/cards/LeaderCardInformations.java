package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;

import java.util.ArrayList;
import java.util.List;

public abstract class LeaderCardInformations extends CardInformations
{
	private final List<LeaderCardConditionsOption> conditionsOptions;

	public LeaderCardInformations(String texturePath, String description, List<LeaderCardConditionsOption> conditionsOptions)
	{
		super(texturePath, description);
		this.conditionsOptions = new ArrayList<>(conditionsOptions);
	}

	public List<LeaderCardConditionsOption> getConditionsOptions()
	{
		return this.conditionsOptions;
	}
}
