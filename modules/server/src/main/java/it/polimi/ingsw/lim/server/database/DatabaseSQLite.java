package it.polimi.ingsw.lim.server.database;

import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseSQLite extends Database
{
	private final String databaseLocation;

	public DatabaseSQLite(String databaseLocation)
	{
		this.databaseLocation = databaseLocation;
	}

	@Override
	public Connection openConnection()
	{
		try {
			if (new File(this.databaseLocation).createNewFile()) {
				Server.getLogger().log(Level.SEVERE, "Database created.");
			}
		} catch (IOException exception) {
			Server.getLogger().log(Level.INFO, "Unable to create database.", exception);
		}
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:" + this.databaseLocation);
		} catch (ClassNotFoundException | SQLException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			return null;
		}
	}
}
