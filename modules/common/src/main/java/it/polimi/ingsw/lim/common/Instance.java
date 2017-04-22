package it.polimi.ingsw.lim.common;

import it.polimi.ingsw.lim.common.enums.Side;

public abstract class Instance
{
	protected Side side;

	protected Instance(Side side)
	{
		this.side = side;
	}

	public Side getSide()
	{
		return this.side;
	}
}
