package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;

import java.util.ArrayList;
import java.util.List;

public class LeaderCardInformations extends CardInformations
{
	private final List<LeaderCardConditionsOption> conditionsOptions;

	public LeaderCardInformations(int index, String texturePath, String description, List<LeaderCardConditionsOption> conditionsOptions)
	{
		super(index, texturePath, description);
		this.conditionsOptions = new ArrayList<>(conditionsOptions);
	}

	public List<LeaderCardConditionsOption> getConditionsOptions()
	{
		return this.conditionsOptions;
	}
}
