package it.polimi.ingsw.lim.server.utils;

import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.Room;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.enums.Command;
import it.polimi.ingsw.lim.server.enums.QueryRead;
import it.polimi.ingsw.lim.server.enums.QueryWrite;
import it.polimi.ingsw.lim.server.gui.ControllerMain;
import it.polimi.ingsw.lim.server.network.Connection;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

public class Utils
{
	public static final String DATABASE_TABLE_PREFIX = "lim_";

	private Utils()
	{
	}

	/**
	 * Checks online for the external IP address.
	 * @return a string representing the IP address if successful, otherwise null.
	 */
	public static String getExternalIpAddress()
	{
		try {
			URL myIP = new URL("http://checkip.amazonaws.com");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myIP.openStream()));
			return bufferedReader.readLine();
		} catch (IOException exception) {
			Server.getLogger().log(Level.INFO, "Cannot retrieve IP address...", exception);
		}
		return null;
	}

	/**
	 * Prints a string to the log visible in the main screen.
	 * @param text the string to print.
	 */
	public static void displayToLog(String text)
	{
		if (!(Server.getInstance().getWindowInformations().getController() instanceof ControllerMain)) {
			return;
		}
		Platform.runLater(() -> {
			if (((ControllerMain) Server.getInstance().getWindowInformations().getController()).getLogTextArea().getText().length() < 1) {
				((ControllerMain) Server.getInstance().getWindowInformations().getController()).getLogTextArea().appendText(text);
			} else {
				((ControllerMain) Server.getInstance().getWindowInformations().getController()).getLogTextArea().appendText("\n" + text);
			}
		});
	}

	/**
	 * Executes the given command.
	 * @param command the command to execute.
	 */
	public static void executeCommand(String command)
	{
		Utils.displayToLog("[Command]: " + command);
		String commandType = command;
		String commandArguments = null;
		if (command.contains(" ")) {
			commandType = commandType.substring(0, commandType.indexOf(' '));
			commandArguments = command.replaceAll(commandType + " ", "");
		}
		try {
			switch (Command.valueOf(commandType.toUpperCase(Locale.ENGLISH))) {
				case SAY:
					Command.handleSayCommand(commandArguments);
					break;
				case KICK:
					Command.handleKickCommand(commandArguments);
					break;
				case KICKID:
					Command.handleKickIdCommand(commandArguments);
					break;
				default:
			}
		} catch (IllegalArgumentException exception) {
			Server.getLogger().log(Level.INFO, "Command does not exist.", exception);
			Utils.displayToLog("Command does not exist.");
		}
	}

	/**
	 * Converts a list of Room objects to a list of RoomInformations objects.
	 * @return the list to convert.
	 */
	public static List<RoomInformations> convertRoomsToInformations()
	{
		List<RoomInformations> rooms = new ArrayList<>();
		for (Room room : Server.getInstance().getRooms()) {
			if (room.getPlayers().size() > 3) {
				continue;
			}
			List<String> playerNames = new ArrayList<>();
			for (Connection player : room.getPlayers()) {
				playerNames.add(player.getUsername());
			}
			rooms.add(new RoomInformations(room.getId(), room.getName(), playerNames));
		}
		return rooms;
	}

	/**
	 * Retrieves the players currently not in a room.
	 * @return the list of players currently not in a room
	 */
	public static List<Connection> getPlayersInLobby()
	{
		List<Connection> playersInLobby = new ArrayList<>(Server.getInstance().getConnections());
		playersInLobby.removeAll(Utils.getPlayersInRooms());
		return playersInLobby;
	}

	/**
	 * Retrieves the players currently in a room.
	 * @return the list of players currently in a room
	 */
	public static List<Connection> getPlayersInRooms()
	{
		List<Connection> playersInRooms = new ArrayList<>();
		for (Connection connection : Server.getInstance().getConnections()) {
			boolean isInRoom = false;
			for (Room room : Server.getInstance().getRooms()) {
				if (room.getPlayers().contains(connection)) {
					isInRoom = true;
					break;
				}
			}
			if (isInRoom) {
				playersInRooms.add(connection);
			}
		}
		return playersInRooms;
	}

	public static ResultSet sqlRead(QueryRead query, List<QueryArgument> queryArguments)
	{
		try (PreparedStatement statement = Server.getInstance().getDatabase().getConnection().prepareStatement(query.getText())) {
			Utils.fillStatement(statement, queryArguments);
			return statement.executeQuery();
		} catch (SQLException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			return null;
		}
	}

	public static void sqlWrite(QueryWrite query, List<QueryArgument> queryArguments)
	{
		try (PreparedStatement statement = Server.getInstance().getDatabase().getConnection().prepareStatement(query.getText())) {
			Utils.fillStatement(statement, queryArguments);
			statement.executeUpdate();
		} catch (SQLException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	private static void fillStatement(PreparedStatement preparedStatement, List<QueryArgument> queryArguments) throws SQLException
	{
		if (queryArguments == null) {
			return;
		}
		for (int index = 0; index < queryArguments.size(); index++) {
			QueryArgument queryArgument = queryArguments.get(index);
			switch (queryArguments.get(index).getValueType()) {
				case INTEGER:
					Utils.setStatementInteger(preparedStatement, queryArgument, index);
					break;
				case LONG:
					Utils.setStatementLong(preparedStatement, queryArgument, index);
					break;
				case FLOAT:
					Utils.setStatementFloat(preparedStatement, queryArgument, index);
					break;
				case DOUBLE:
					Utils.setStatementDouble(preparedStatement, queryArgument, index);
					break;
				case STRING:
					Utils.setStatementString(preparedStatement, queryArgument, index);
					break;
			}
		}
	}

	private static void setStatementInteger(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() == null) {
			preparedStatement.setNull(index + 1, Types.INTEGER);
			return;
		}
		try {
			int value = Integer.parseInt(queryArgument.getValue());
			preparedStatement.setInt(index + 1, value);
		} catch (NumberFormatException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	private static void setStatementLong(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() == null) {
			preparedStatement.setNull(index + 1, Types.BIGINT);
			return;
		}
		try {
			long value = Long.parseLong(queryArgument.getValue());
			preparedStatement.setLong(index + 1, value);
		} catch (NumberFormatException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	private static void setStatementFloat(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() == null) {
			preparedStatement.setNull(index + 1, Types.FLOAT);
			return;
		}
		try {
			float value = Float.parseFloat(queryArgument.getValue());
			preparedStatement.setFloat(index + 1, value);
		} catch (NumberFormatException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	private static void setStatementDouble(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() == null) {
			preparedStatement.setNull(index + 1, Types.DOUBLE);
			return;
		}
		try {
			double value = Double.parseDouble(queryArgument.getValue());
			preparedStatement.setDouble(index + 1, value);
		} catch (NumberFormatException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	private static void setStatementString(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() == null) {
			preparedStatement.setNull(index + 1, Types.VARCHAR);
			return;
		}
		preparedStatement.setString(index + 1, queryArgument.getValue());
	}
}
