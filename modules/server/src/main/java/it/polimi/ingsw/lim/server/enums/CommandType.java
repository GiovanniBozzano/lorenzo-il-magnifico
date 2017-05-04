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
		if (text != null) {
			Connection.broadcastChatMessage("[SERVER]: " + text);
		} else {
			Utils.displayToLog("Missing command arguments.");
		}
	}

	public static void handleKickCommand(String text)
	{
		if (text != null) {
			for (Connection connection : Server.getInstance().getConnections()) {
				if (connection.getName().equals(text)) {
					connection.disconnect(true);
					break;
				}
			}
		} else {
			Utils.displayToLog("Missing command arguments.");
		}
	}
}
