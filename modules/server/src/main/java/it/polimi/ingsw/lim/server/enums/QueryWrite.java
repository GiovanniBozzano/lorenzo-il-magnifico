package it.polimi.ingsw.lim.server.enums;

public enum QueryWrite
{
	;
	private final String text;

	QueryWrite(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return this.text;
	}
}
