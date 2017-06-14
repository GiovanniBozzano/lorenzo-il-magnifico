package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.server.enums.LeaderCardType;

import java.util.List;

public abstract class LeaderCard extends Card
{
	private final LeaderCardType leaderCardType;
	private final List<LeaderCardConditionsOption> conditionsOptions;
	private final String description;
	private boolean played = false;

	public LeaderCard(int index, String displayName, LeaderCardType leaderCardType, List<LeaderCardConditionsOption> conditionsOptions, String description)
	{
		super(index, displayName);
		this.leaderCardType = leaderCardType;
		this.conditionsOptions = conditionsOptions;
		this.description = description;
	}

	public LeaderCardType getLeaderCardType()
	{
		return this.leaderCardType;
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
