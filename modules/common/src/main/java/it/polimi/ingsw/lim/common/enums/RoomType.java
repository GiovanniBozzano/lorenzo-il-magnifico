package it.polimi.ingsw.lim.common.enums;

public enum RoomType
{
	NORMAL(4),
	EXTENDED(5);
	private final int playersNumber;

	RoomType(int playersNumber)
	{
		this.playersNumber = playersNumber;
	}

	public int getPlayersNumber()
	{
		return this.playersNumber;
	}
}
