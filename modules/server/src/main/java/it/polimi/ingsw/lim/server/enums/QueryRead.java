package it.polimi.ingsw.lim.server.enums;

public enum QueryRead
{
	;
	private final String text;

	QueryRead(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return this.text;
	}
}
