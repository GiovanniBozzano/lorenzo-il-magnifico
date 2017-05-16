package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.enums.WorkType;

public class BonusAdditionWork extends BonusAddition
{
	private final WorkType workType;

	public BonusAdditionWork(int value, WorkType workType)
	{
		this.value = value;
		this.workType = workType;
	}

	public WorkType getWorkType()
	{
		return this.workType;
	}
}
