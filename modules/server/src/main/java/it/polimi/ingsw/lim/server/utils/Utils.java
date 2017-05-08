package it.polimi.ingsw.lim.server.utils;

import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.Room;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.enums.CommandType;
import it.polimi.ingsw.lim.server.gui.ControllerMain;
import it.polimi.ingsw.lim.server.network.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

public class Utils
{
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
		if (((ControllerMain) Server.getInstance().getWindowInformations().getController()).getLogTextArea().getText().length() < 1) {
			((ControllerMain) Server.getInstance().getWindowInformations().getController()).getLogTextArea().appendText(text);
		} else {
			((ControllerMain) Server.getInstance().getWindowInformations().getController()).getLogTextArea().appendText("\n" + text);
		}
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
			switch (CommandType.valueOf(commandType.toUpperCase(Locale.ENGLISH))) {
				case SAY:
					CommandType.handleSayCommand(commandArguments);
					break;
				case KICK:
					CommandType.handleKickCommand(commandArguments);
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
			List<String> playerNames = new ArrayList<>();
			for (Connection player : room.getPlayers()) {
				playerNames.add(player.getName());
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
}
