package it.polimi.ingsw.lim.server.enums;

import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

public enum CommandType
{
	SAY,
	KICK;

	public static void handleSayCommand(String text)
	{
		if (text != null && !text.replaceAll("^\\s+|\\s+$", "").isEmpty()) {
			Connection.broadcastChatMessage("[SERVER]: " + text.replaceAll("^\\s+|\\s+$", ""));
		} else {
			Utils.displayToLog("Missing command arguments.");
		}
	}

	public static void handleKickCommand(String text)
	{
		if (text != null) {
			boolean found = false;
			for (Connection connection : Server.getInstance().getConnections()) {
				if (connection.getName().equals(text)) {
					found = true;
					connection.disconnect(true);
					break;
				}
			}
			if (!found) {
				Utils.displayToLog("Player does not exist.");
			}
		} else {
			Utils.displayToLog("Missing command arguments.");
		}
	}
}
