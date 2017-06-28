package it.polimi.ingsw.lim.server.enums;

import it.polimi.ingsw.lim.server.database.Database;

public enum QueryWrite
{
	REGISTER_PLAYER("INSERT INTO " + Database.PREFIX + Database.TABLE_PLAYERS + " (" + Database.TABLE_PLAYERS_COLUMN_USERNAME + ", " + Database.TABLE_PLAYERS_COLUMN_PASSWORD + ", " + Database.TABLE_PLAYERS_COLUMN_SALT + ") VALUES (?, ?, ?);"),
	UPDATE_PLAYER_VICTORY("UPDATE " + Database.PREFIX + Database.TABLE_PLAYERS + " SET " + Database.TABLE_PLAYERS_COLUMN_WON_MATCHES + " = " + Database.TABLE_PLAYERS_COLUMN_WON_MATCHES + " + 1 WHERE " + Database.TABLE_PLAYERS_COLUMN_USERNAME + " = ?;"),
	UPDATE_PLAYER_DEFEAT("UPDATE " + Database.PREFIX + Database.TABLE_PLAYERS + " SET " + Database.TABLE_PLAYERS_COLUMN_LOST_MATCHES + " = " + Database.TABLE_PLAYERS_COLUMN_LOST_MATCHES + " + 1 WHERE " + Database.TABLE_PLAYERS_COLUMN_USERNAME + " = ?;"),
	UPDATE_PLAYER_VICTORY_POINT_RECORD("UPDATE " + Database.PREFIX + Database.TABLE_PLAYERS + " SET " + Database.TABLE_PLAYERS_COLUMN_VICTORY_POINTS_RECORD + " = ? WHERE " + Database.TABLE_PLAYERS_COLUMN_USERNAME + " = ?;");
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
