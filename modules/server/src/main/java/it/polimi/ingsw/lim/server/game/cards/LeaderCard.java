package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.LeaderCardConditionsOption;

import java.util.List;

public abstract class LeaderCard extends Card
{
	private final List<LeaderCardConditionsOption> conditionsOptions;
	private final String description;
	private boolean played = false;

	public LeaderCard(String displayName, int index, List<LeaderCardConditionsOption> conditionsOptions, String description)
	{
		super(displayName, index);
		this.conditionsOptions = conditionsOptions;
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

	public boolean isPlayed()
	{
		return this.played;
	}

	public void setPlayed(boolean played)
	{
		this.played = played;
	}
}
