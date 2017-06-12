package it.polimi.ingsw.lim.server.game.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.utils.BoardPositionInformations;
import it.polimi.ingsw.lim.server.game.utils.CouncilPalaceReward;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;

public class BoardHandler
{
	private static final Map<BoardPosition, BoardPositionInformations> BOARD_POSITIONS_INFORMATIONS = new BoardPositionsInstantRewardsBuilder("/json/board_positions_instant_rewards.json").initialize();
	protected static final List<CouncilPalaceReward> COUNCIL_PRIVILEGE_REWARDS = new CouncilPrivilegeRewardsBuilder("/json/council_privilege_rewards.json").initialize();
	private final List<Connection> councilPalaceOrder = new LinkedList<>();

	public static BoardPositionInformations getBoardPositionInformations(BoardPosition boardPosition)
	{
		if (BoardHandler.BOARD_POSITIONS_INFORMATIONS.containsKey(boardPosition)) {
			return BoardHandler.BOARD_POSITIONS_INFORMATIONS.get(boardPosition);
		}
		return new BoardPositionInformations(0, new ArrayList<>());
	}

	public List<Connection> getCouncilPalaceOrder()
	{
		return this.councilPalaceOrder;
	}

	private static class BoardPositionsInstantRewardsBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = BoardPositionsInstantRewardsBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		BoardPositionsInstantRewardsBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<BoardPosition, BoardPositionInformations> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return BoardPositionsInstantRewardsBuilder.GSON.fromJson(reader, new TypeToken<Map<BoardPosition, ResourceAmount[]>>()
				{
				}.getType());
			} catch (IOException exception) {
				Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new HashMap<>();
		}
	}

	private static class CouncilPrivilegeRewardsBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = CouncilPrivilegeRewardsBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		CouncilPrivilegeRewardsBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		List<CouncilPalaceReward> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return CouncilPrivilegeRewardsBuilder.GSON.fromJson(reader, new TypeToken<List<CouncilPalaceReward>>()
				{
				}.getType());
			} catch (IOException exception) {
				Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new ArrayList<>();
		}
	}
}
