package it.polimi.ingsw.lim.common.cards;

import it.polimi.ingsw.lim.common.enums.Period;

public abstract class DevelopmentCard
{
	private final String displayName;
	private final Period period;

	DevelopmentCard(String displayName, Period period)
	{
		this.displayName = displayName;
		this.period = period;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}

	public Period getPeriod()
	{
		return this.period;
	}
}
