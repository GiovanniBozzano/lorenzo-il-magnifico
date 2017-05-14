package it.polimi.ingsw.lim.server.database;

import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class Database
{
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
			this.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + Utils.DATABASE_TABLE_PREFIX + "groups (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(256) NOT NULL);");
			this.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + Utils.DATABASE_TABLE_PREFIX + "npcs (id INTEGER PRIMARY KEY AUTOINCREMENT, npc_id INTEGER(11) NOT NULL, group_id INTEGER(11) NOT NULL);");
		} catch (SQLException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}
}
