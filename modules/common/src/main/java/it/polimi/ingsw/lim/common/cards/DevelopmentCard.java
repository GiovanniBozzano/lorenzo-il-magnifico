package it.polimi.ingsw.lim.common.cards;

import it.polimi.ingsw.lim.common.enums.Period;

public abstract class DevelopmentCard extends Card
{
	private final Period period;

	DevelopmentCard(String displayName, Period period)
	{
		super(displayName);
		this.period = period;
	}

	public Period getPeriod()
	{
		return this.period;
	}
}
