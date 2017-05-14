package it.polimi.ingsw.lim.server.database;

import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class Database
{
	public static final String PREFIX = "lim_";
	public static final String DATABASE_FILE = "database.db";
	public static final String TABLE_PLAYERS = "players";
	public static final String TABLE_PLAYERS_COLUMN_ID = "id";
	public static final String TABLE_PLAYERS_COLUMN_USERNAME = "username";
	@SuppressWarnings("squid:S2068") public static final String TABLE_PLAYERS_COLUMN_PASSWORD = "password";
	public static final String TABLE_PLAYERS_COLUMN_SALT = "salt";
	private Connection connection;

	protected abstract Connection openConnection();

	public Connection getConnection()
	{
		if (this.connection == null) {
			this.connection = this.openConnection();
		}
		return this.connection;
	}

	public void closeConnection()
	{
		try {
			this.connection.close();
		} catch (SQLException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		this.connection = null;
	}

	public void createTables()
	{
		try {
			this.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + Database.PREFIX + Database.TABLE_PLAYERS + " (" + Database.TABLE_PLAYERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Database.TABLE_PLAYERS_COLUMN_USERNAME + " VARCHAR(16) NOT NULL, " + Database.TABLE_PLAYERS_COLUMN_PASSWORD + " CHAR(128) NOT NULL, " + Database.TABLE_PLAYERS_COLUMN_SALT + " RAW NOT NULL);");
		} catch (SQLException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}
}
