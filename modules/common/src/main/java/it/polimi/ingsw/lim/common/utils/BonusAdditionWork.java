package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.Work;

public class BonusAdditionWork extends BonusAddition
{
	private Work workType;

	public BonusAdditionWork(int value, Work workType)
	{
		this.value = value;
		this.workType = workType;
	}

	public Work getWorkType()
	{
		return workType;
	}
}
