package it.polimi.ingsw.lim.common.events;

import it.polimi.ingsw.lim.common.enums.WorkType;

public class EventWork extends Event
{
	private final WorkType workType;

	public EventWork(int value, WorkType workType)
	{
		this.value = value;
		this.workType = workType;
	}

	public WorkType getWorkType()
	{
		return this.workType;
	}
}
