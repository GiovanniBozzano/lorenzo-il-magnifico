package it.polimi.ingsw.lim.server.game.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.game.CouncilPalaceReward;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.logging.Level;

public class BoardHandler
{
	public static final List<CouncilPalaceReward> COUNCIL_PALACE_REWARDS = new CouncilPalaceRewardsBuilder("/json/council_privilege_rewards.json").initialize();

	private static class CouncilPalaceRewardsBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = CouncilPalaceRewardsBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		CouncilPalaceRewardsBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		List<CouncilPalaceReward> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return CouncilPalaceRewardsBuilder.GSON.fromJson(reader, new TypeToken<List<CouncilPalaceReward>>()
				{
				}.getType());
			} catch (IOException exception) {
				Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			return null;
		}
	}
}
