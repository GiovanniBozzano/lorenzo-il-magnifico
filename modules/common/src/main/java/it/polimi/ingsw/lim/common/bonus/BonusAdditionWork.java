package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.enums.Work;

public class BonusAdditionWork extends BonusAddition
{
	private final Work workType;

	public BonusAdditionWork(int value, Work workType)
	{
		this.value = value;
		this.workType = workType;
	}

	public Work getWorkType()
	{
		return this.workType;
	}
}
