package it.polimi.ingsw.lim.server;

public class ServerSettings
{
	private static final ServerSettings INSTANCE = new ServerSettings();
	private static final int roomTimer = 5;

	private ServerSettings()
	{
	}

	public static ServerSettings getInstance()
	{
		return ServerSettings.INSTANCE;
	}

	public int getRoomTimer()
	{
		return ServerSettings.roomTimer;
	}
}
