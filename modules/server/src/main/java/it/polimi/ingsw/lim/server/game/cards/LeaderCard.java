package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditionsOption;

import java.util.List;

public abstract class LeaderCard extends Card
{
	private final List<CardLeaderConditionsOption> conditionsOptions;
	private final String description;
	private boolean played = false;

	public LeaderCard(String displayName, int index, List<CardLeaderConditionsOption> conditionsOptions, String description)
	{
		super(displayName, index);
		this.conditionsOptions = conditionsOptions;
		this.description = description;
	}

	public List<CardLeaderConditionsOption> getConditionsOptions()
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
