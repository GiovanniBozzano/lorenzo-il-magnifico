package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformations;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.server.enums.LeaderCardType;

import java.util.List;

public abstract class LeaderCard extends Card
{
	private final LeaderCardType leaderCardType;
	private final List<LeaderCardConditionsOption> conditionsOptions;
	private final String description;
	private boolean played = false;

	protected LeaderCard(int index, String texturePath, String displayName, String description, LeaderCardType leaderCardType, List<LeaderCardConditionsOption> conditionsOptions)
	{
		super(index, texturePath, displayName);
		this.description = description;
		this.leaderCardType = leaderCardType;
		this.conditionsOptions = conditionsOptions;
	}

	public abstract LeaderCardInformations getInformations();

	public String getDescription()
	{
		return this.description;
	}

	public LeaderCardType getLeaderCardType()
	{
		return this.leaderCardType;
	}

	public List<LeaderCardConditionsOption> getConditionsOptions()
	{
		return this.conditionsOptions;
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
