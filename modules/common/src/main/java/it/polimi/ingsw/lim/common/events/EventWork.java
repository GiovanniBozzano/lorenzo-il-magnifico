package it.polimi.ingsw.lim.common.events;

import it.polimi.ingsw.lim.common.enums.Work;

public class EventWork extends Event
{
	private final Work workType;

	public EventWork(int value, Work workType)
	{
		this.value = value;
		this.workType = workType;
	}

	public Work getWorkType()
	{
		return workType;
	}
}
