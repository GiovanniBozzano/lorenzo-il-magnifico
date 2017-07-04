package it.polimi.ingsw.lim.common.enums;

/**
 * <p>This class is used to represent the type of room, with a number of players
 * and the corresponding string
 */
public enum RoomType
{
	NORMAL(4, "Normal"),
	EXTENDED(5, "Extended");
	private final int playersNumber;
	private final String displayName;

	RoomType(int playersNumber, String displayName)
	{
		this.playersNumber = playersNumber;
		this.displayName = displayName;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}

	public int getPlayersNumber()
	{
		return this.playersNumber;
	}
}
