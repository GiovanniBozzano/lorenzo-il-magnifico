package it.polimi.ingsw.lim.server.enums;

import it.polimi.ingsw.lim.server.database.Database;

public enum QueryRead
{
	CHECK_PLAYER_PASSWORD("SELECT " + Database.TABLE_PLAYERS_COLUMN_PASSWORD + ", " + Database.TABLE_PLAYERS_COLUMN_SALT + " FROM " + Database.PREFIX + Database.TABLE_PLAYERS + " WHERE " + Database.TABLE_PLAYERS_COLUMN_USERNAME + " = ?;"),
	CHECK_EXISTING_PLAYER_USERNAME("SELECT 1 FROM " + Database.PREFIX + Database.TABLE_PLAYERS + " WHERE " + Database.TABLE_PLAYERS_COLUMN_USERNAME + " = ?;"),
	GET_PLAYER_VICTORY_POINTS_RECORD("SELECT " + Database.TABLE_PLAYERS_COLUMN_VICTORY_POINTS_RECORD + " FROM " + Database.PREFIX + Database.TABLE_PLAYERS + " WHERE " + Database.TABLE_PLAYERS_COLUMN_USERNAME + " = ?;");
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
