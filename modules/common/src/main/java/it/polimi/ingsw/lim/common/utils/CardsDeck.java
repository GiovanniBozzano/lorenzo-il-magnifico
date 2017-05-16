package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.DevelopmentCard;

public class CardsDeck<T extends DevelopmentCard>
{
	private final T[] firstPeriod;
	private final T[] secondPeriod;
	private final T[] thirdPeriod;

	public CardsDeck(T[] firstPeriod, T[] secondPeriod, T[] thirdPeriod)
	{
		this.firstPeriod = firstPeriod;
		this.secondPeriod = secondPeriod;
		this.thirdPeriod = thirdPeriod;
	}

	public T[] getFirstPeriod()
	{
		return this.firstPeriod;
	}

	public T[] getSecondPeriod()
	{
		return this.secondPeriod;
	}

	public T[] getThirdPeriod()
	{
		return this.thirdPeriod;
	}
}
