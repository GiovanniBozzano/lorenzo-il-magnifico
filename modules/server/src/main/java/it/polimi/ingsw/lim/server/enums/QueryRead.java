package it.polimi.ingsw.lim.server.enums;

import it.polimi.ingsw.lim.server.database.Database;

public enum QueryRead
{
	GET_SALT_AND_PASSWORD_WITH_USERNAME("SELECT " + Database.TABLE_PLAYERS_COLUMN_PASSWORD + " AND " + Database.TABLE_PLAYERS_COLUMN_SALT + " FROM " + Database.PREFIX + Database.TABLE_PLAYERS + " WHERE " + Database.TABLE_PLAYERS_COLUMN_USERNAME + " = ? LIMIT 1");
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
