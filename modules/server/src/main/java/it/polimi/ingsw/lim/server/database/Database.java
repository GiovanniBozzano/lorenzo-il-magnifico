package it.polimi.ingsw.lim.server.database;

import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class Database
{
	public static final String PREFIX = "lim_";
	public static final String DATABASE_FILE = "database.db";
	public static final String TABLE_PLAYERS = "players";
	public static final String TABLE_PLAYERS_COLUMN_USERNAME = "username";
	@SuppressWarnings("squid:S2068") public static final String TABLE_PLAYERS_COLUMN_PASSWORD = "password";
	public static final String TABLE_PLAYERS_COLUMN_SALT = "salt";
	public static final String TABLE_PLAYERS_COLUMN_WON_MATCHES = "won_matches";
	public static final String TABLE_PLAYERS_COLUMN_LOST_MATCHES = "lost_matches";
	public static final String TABLE_PLAYERS_COLUMN_VICTORY_POINTS_RECORD = "victory_points_records";
	public static final String TABLE_PLAYERS_COLUMN_GOOD_GAMES = "good_games";
	private static final String TABLE_PLAYERS_COLUMN_ID = "id";
	private Connection connection;

	protected abstract Connection openConnection();

	public void closeConnection()
	{
		try {
			this.connection.close();
		} catch (SQLException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
		this.connection = null;
	}

	public void createTables()
	{
		try {
			this.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + Database.PREFIX + Database.TABLE_PLAYERS + " (" + Database.TABLE_PLAYERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Database.TABLE_PLAYERS_COLUMN_USERNAME + " VARCHAR(16) NOT NULL, " + Database.TABLE_PLAYERS_COLUMN_PASSWORD + " CHAR(128) NOT NULL, " + Database.TABLE_PLAYERS_COLUMN_SALT + " BLOB NOT NULL, " + Database.TABLE_PLAYERS_COLUMN_WON_MATCHES + " INTEGER DEFAULT 0 NOT NULL, " + Database.TABLE_PLAYERS_COLUMN_LOST_MATCHES + " INTEGER DEFAULT 0 NOT NULL, " + Database.TABLE_PLAYERS_COLUMN_VICTORY_POINTS_RECORD + " INTEGER DEFAULT 0 NOT NULL, " + Database.TABLE_PLAYERS_COLUMN_GOOD_GAMES + " INTEGER DEFAULT 0 NOT NULL);");
		} catch (SQLException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public Connection getConnection()
	{
		if (this.connection == null) {
			this.connection = this.openConnection();
		}
		return this.connection;
	}
}
