package it.polimi.ingsw.lim.common.enums;

public enum Color
{
	BLUE("#116DB5"),
	GREEN("#1C7E3E"),
	PURPLE("#8B578F"),
	RED("#C20E34"),
	YELLOW("#FDCF0B");
	private String hex;

	Color(String hex)
	{
		this.hex = hex;
	}

	public String getHex()
	{
		return this.hex;
	}
}
