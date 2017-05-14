package it.polimi.ingsw.lim.server.enums;

import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

public enum CommandType
{
	SAY,
	KICK,
	KICKID;

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
				if (connection.getUsername().equals(text)) {
					found = true;
					connection.disconnect(true, null);
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

	public static void handleKickIdCommand(String text)
	{
		if (text != null) {
			if (!CommonUtils.isInteger(text)) {
				Utils.displayToLog("Missing command arguments.");
				return;
			}
			boolean found = false;
			for (Connection connection : Server.getInstance().getConnections()) {
				if (connection.getId() == Integer.parseInt(text)) {
					found = true;
					connection.disconnect(true, null);
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
