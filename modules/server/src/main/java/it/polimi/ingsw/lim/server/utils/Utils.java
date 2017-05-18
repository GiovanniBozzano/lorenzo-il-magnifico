package it.polimi.ingsw.lim.server.utils;

import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.enums.Command;
import it.polimi.ingsw.lim.server.enums.QueryRead;
import it.polimi.ingsw.lim.server.enums.QueryWrite;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.gui.ControllerMain;
import it.polimi.ingsw.lim.server.network.Connection;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
	public static final String SCENE_MAIN = "/fxml/SceneMain.fxml";
	public static final String SCENE_START = "/fxml/SceneStart.fxml";

	private Utils()
	{
	}

	/**
	 * Checks online for the external IP address.
	 *
	 * @return a string representing the IP address if successful, otherwise
	 * null.
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
	 *
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
	 *
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
	 *
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
	 *
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
	 *
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

	/**
	 * Executes a read query on the database with a prepared statement.
	 *
	 * @param query the query to execute.
	 * @param queryArguments the arguments to fill the statement.
	 *
	 * @return the rows returned by the query.
	 *
	 * @throws SQLException if the query was not successful.
	 */
	public static ResultSet sqlRead(QueryRead query, List<QueryArgument> queryArguments) throws SQLException
	{
		Server.getLogger().log(Level.INFO, query.getText());
		try {
			PreparedStatement preparedStatement = Server.getInstance().getDatabase().getConnection().prepareStatement(query.getText());
			Utils.fillStatement(preparedStatement, queryArguments);
			return preparedStatement.executeQuery();
		} catch (SQLException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			throw exception;
		}
	}

	/**
	 * Executes a write query on the database with a prepared statement.
	 *
	 * @param query the query to execute.
	 * @param queryArguments the arguments to use to fill the prepared
	 * statement.
	 *
	 * @throws SQLException if the query was not successful.
	 */
	public static void sqlWrite(QueryWrite query, List<QueryArgument> queryArguments) throws SQLException
	{
		Server.getLogger().log(Level.INFO, query.getText());
		try (PreparedStatement preparedStatement = Server.getInstance().getDatabase().getConnection().prepareStatement(query.getText())) {
			Utils.fillStatement(preparedStatement, queryArguments);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			throw exception;
		}
	}

	/**
	 * Fills a prepared statement with the given arguments.
	 *
	 * @param preparedStatement the prepared statement to fill.
	 * @param queryArguments the arguments to use to fill the prepared
	 * statement.
	 *
	 * @throws SQLException if the process was not successful.
	 */
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
				case BYTES:
					Utils.setStatementBytes(preparedStatement, queryArgument, index);
					break;
				default:
			}
		}
	}

	/**
	 * Sets the given prepared statement argument to the given integer.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementInteger(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof Integer) {
			preparedStatement.setInt(index + 1, (Integer) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.INTEGER);
	}

	/**
	 * Sets the given prepared statement argument to the given long.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementLong(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof Long) {
			preparedStatement.setLong(index + 1, (Long) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.BIGINT);
	}

	/**
	 * Sets the given prepared statement argument to the given float.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementFloat(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof Float) {
			preparedStatement.setFloat(index + 1, (Float) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.FLOAT);
	}

	/**
	 * Sets the given prepared statement argument to the given double.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementDouble(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof Double) {
			preparedStatement.setDouble(index + 1, (Double) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.DOUBLE);
	}

	/**
	 * Sets the given prepared statement argument to the given string.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementString(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof String) {
			preparedStatement.setString(index + 1, (String) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.VARCHAR);
	}

	/**
	 * Sets the given prepared statement argument to the given bytes array.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementBytes(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof byte[]) {
			preparedStatement.setBytes(index + 1, (byte[]) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.BLOB);
	}

	/**
	 * Uses SHA-512 to encrypt a string with the given salt.
	 *
	 * @param text the string to encrypt.
	 * @param salt the salt to use.
	 *
	 * @return the encrypted string.
	 *
	 * @throws NoSuchAlgorithmException if the process was not successful.
	 */
	public static String sha512Encrypt(String text, byte[] salt) throws NoSuchAlgorithmException
	{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
		messageDigest.update(salt);
		byte[] bytes = messageDigest.digest(text.getBytes());
		StringBuilder stringBuilder = new StringBuilder();
		for (byte currentByte : bytes) {
			stringBuilder.append(Integer.toString((currentByte & 0xFF) + 0x100, 16).substring(1));
		}
		return stringBuilder.toString();
	}

	/**
	 * Generates a random salt made as a 16 bytes array.
	 *
	 * @return the generated salt.
	 *
	 * @throws NoSuchAlgorithmException if the process was not successful.
	 */
	public static byte[] getSalt() throws NoSuchAlgorithmException
	{
		byte[] salt = new byte[16];
		SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);
		return salt;
	}
}
