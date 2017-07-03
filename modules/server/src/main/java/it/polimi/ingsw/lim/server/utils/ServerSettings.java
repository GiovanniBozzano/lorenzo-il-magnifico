package it.polimi.ingsw.lim.server.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

public class ServerSettings
{
	private static ServerSettings instance;
	private final int roomTimer;
	private final int extendedRoomTimer;
	private final int personalBonusTileChoiceTimer;
	private final int leaderCardsChoiceTimer;
	private final int excommunicationChoiceTimer;
	private final int gameActionTimer;
	private final int endGameTimer;

	private ServerSettings(int roomTimer, int extendedRoomTimer, int personalBonusTileChoiceTimer, int leaderCardsChoiceTimer, int excommunicationChoiceTimer, int gameActionTimer, int endGameTimer)
	{
		this.roomTimer = roomTimer;
		this.extendedRoomTimer = extendedRoomTimer;
		this.personalBonusTileChoiceTimer = personalBonusTileChoiceTimer;
		this.leaderCardsChoiceTimer = leaderCardsChoiceTimer;
		this.excommunicationChoiceTimer = excommunicationChoiceTimer;
		this.gameActionTimer = gameActionTimer;
		this.endGameTimer = endGameTimer;
	}

	public static ServerSettings getInstance()
	{
		if (ServerSettings.instance == null) {
			ServerSettings.instance = new ServerSettingsBuilder("/json/timers.json").initialize();
		}
		return ServerSettings.instance;
	}

	public int getRoomTimer()
	{
		return this.roomTimer;
	}

	public int getExtendedRoomTimer()
	{
		return this.extendedRoomTimer;
	}

	public int getPersonalBonusTileChoiceTimer()
	{
		return this.personalBonusTileChoiceTimer;
	}

	public int getLeaderCardsChoiceTimer()
	{
		return this.leaderCardsChoiceTimer;
	}

	public int getExcommunicationChoiceTimer()
	{
		return this.excommunicationChoiceTimer;
	}

	public int getGameActionTimer()
	{
		return this.gameActionTimer;
	}

	public int getEndGameTimer()
	{
		return this.endGameTimer;
	}

	private static class ServerSettingsBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = ServerSettingsBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		ServerSettingsBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		ServerSettings initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return ServerSettingsBuilder.GSON.fromJson(reader, ServerSettings.class);
			} catch (IOException exception) {
				Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new ServerSettings(60, 5, 60, 60, 60, 120, 60);
		}
	}
}
