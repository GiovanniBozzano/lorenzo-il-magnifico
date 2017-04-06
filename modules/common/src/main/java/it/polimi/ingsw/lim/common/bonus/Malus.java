package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.enums.Row;

public class Malus
{
	private final Row[] rows;

	public Malus(Row[] rows)
	{
		this.rows = rows;
	}

	public Row[] getRows()
	{
		return rows;
	}
}
