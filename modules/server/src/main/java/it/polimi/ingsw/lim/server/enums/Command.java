package it.polimi.ingsw.lim.server.enums;

import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;

public enum Command
{
	SAY,
	KICK;

	public static void handleSayCommand(String text)
	{
		if (text != null && !text.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "").isEmpty()) {
			Connection.broadcastChatMessage("[SERVER]: " + text.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, ""));
		} else {
			Server.getInstance().getInterfaceHandler().displayToLog("Missing command arguments.");
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
				Server.getInstance().getInterfaceHandler().displayToLog("Player does not exist.");
			}
		} else {
			Server.getInstance().getInterfaceHandler().displayToLog("Missing command arguments.");
		}
	}
}
