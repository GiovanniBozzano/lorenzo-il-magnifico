package it.polimi.ingsw.lim.server.game.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.modifiers.*;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.BoardPositionInformation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

/**
 * <p>This class handles a match board information. It is used to store game
 * board data.
 */
public class BoardHandler
{
	private static final Map<BoardPosition, BoardPositionInformation> BOARD_POSITIONS_INFORMATION = new BoardPositionsInformationBuilder("/json/board_positions_information.json").initialize();
	private static final Map<Period, List<ExcommunicationTile>> EXCOMMUNICATION_TILES = new ExcommunicationTilesBuilder("/json/excommunication_tiles.json").initialize();
	private static final List<PersonalBonusTile> PERSONAL_BONUS_TILES = new PersonalBonusTilesBuilder("/json/personal_bonus_tiles.json").initialize();
	private static final Map<Integer, List<ResourceAmount>> COUNCIL_PRIVILEGE_REWARDS = new CouncilPrivilegeRewardsBuilder("/json/council_privilege_rewards.json").initialize();
	private final Map<Period, ExcommunicationTile> matchExcommunicationTiles;
	private final Map<Integer, List<ResourceAmount>> matchCouncilPrivilegeRewards;
	private final List<Player> councilPalaceOrder = new LinkedList<>();

	public BoardHandler(Map<Period, ExcommunicationTile> matchExcommunicationTiles, Map<Integer, List<ResourceAmount>> matchCouncilPrivilegeRewards)
	{
		this.matchExcommunicationTiles = new EnumMap<>(matchExcommunicationTiles);
		this.matchCouncilPrivilegeRewards = new HashMap<>(matchCouncilPrivilegeRewards);
	}

	public static BoardPositionInformation getBoardPositionInformation(BoardPosition boardPosition)
	{
		if (BoardHandler.BOARD_POSITIONS_INFORMATION.containsKey(boardPosition)) {
			return BoardHandler.BOARD_POSITIONS_INFORMATION.get(boardPosition);
		}
		return new BoardPositionInformation(0, new ArrayList<>());
	}

	public static Map<Period, List<ExcommunicationTile>> getExcommunicationTiles()
	{
		return BoardHandler.EXCOMMUNICATION_TILES;
	}

	public static List<PersonalBonusTile> getPersonalBonusTiles()
	{
		return BoardHandler.PERSONAL_BONUS_TILES;
	}

	public static Map<Integer, List<ResourceAmount>> getCouncilPrivilegeRewards()
	{
		return BoardHandler.COUNCIL_PRIVILEGE_REWARDS;
	}

	public Map<Period, Integer> getMatchExcommunicationTilesIndexes()
	{
		Map<Period, Integer> matchExcommunicationTilesIndexes = new EnumMap<>(Period.class);
		for (Entry<Period, ExcommunicationTile> excommunicationTile : this.matchExcommunicationTiles.entrySet()) {
			matchExcommunicationTilesIndexes.put(excommunicationTile.getKey(), excommunicationTile.getValue().getIndex());
		}
		return matchExcommunicationTilesIndexes;
	}

	public Map<Period, ExcommunicationTile> getMatchExcommunicationTiles()
	{
		return this.matchExcommunicationTiles;
	}

	public Map<Integer, List<ResourceAmount>> getMatchCouncilPrivilegeRewards()
	{
		return this.matchCouncilPrivilegeRewards;
	}

	public List<Player> getCouncilPalaceOrder()
	{
		return this.councilPalaceOrder;
	}

	private static class BoardPositionsInformationBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = BoardPositionsInformationBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		BoardPositionsInformationBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<BoardPosition, BoardPositionInformation> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return BoardPositionsInformationBuilder.GSON.fromJson(reader, new TypeToken<Map<BoardPosition, BoardPositionInformation>>()
				{
				}.getType());
			} catch (IOException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new EnumMap<>(BoardPosition.class);
		}
	}

	private static class ExcommunicationTilesBuilder
	{
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierFirstTurn.class, "FIRST_TURN").registerSubtype(ModifierGainResources.class, "GAIN_RESOURCES").registerSubtype(ModifierHarvest.class, "HARVEST").registerSubtype(ModifierPickDevelopmentCardValue.class, "PICK_DEVELOPMENT_CARD_VALUE").registerSubtype(ModifierPlaceFamilyMemberCancel.class, "PLACE_FAMILY_MEMBER_CANCEL").registerSubtype(ModifierPlaceFamilyMemberValue.class, "PLACE_FAMILY_MEMBER_VALUE").registerSubtype(ModifierPostVictoryPointsCalculationBuildings.class, "POST_VICTORY_POINTS_CALCULATION_BUILDINGS").registerSubtype(ModifierPostVictoryPointsCalculationResources.class, "POST_VICTORY_POINTS_CALCULATION_RESOURCES").registerSubtype(ModifierPreVictoryPointsCalculation.class, "PRE_VICTORY_POINTS_CALCULATION").registerSubtype(ModifierProductionStart.class, "PRODUCTION_START").registerSubtype(ModifierUseServants.class, "USE_SERVANTS").registerSubtype(ModifierVictoryPointsCalculationCharacters.class, "VICTORY_POINTS_CALCULATION_CHARACTERS").registerSubtype(ModifierVictoryPointsCalculationTerritories.class, "VICTORY_POINTS_CALCULATION_TERRITORIES").registerSubtype(ModifierVictoryPointsCalculationVentures.class, "VICTORY_POINTS_CALCULATION_VENTURES");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(ExcommunicationTilesBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER);
		private static final Gson GSON = ExcommunicationTilesBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		ExcommunicationTilesBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Period, List<ExcommunicationTile>> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				Map<Period, List<ExcommunicationTile>> excommunicationTiles = ExcommunicationTilesBuilder.GSON.fromJson(reader, new TypeToken<Map<Period, List<ExcommunicationTile>>>()
				{
				}.getType());
				for (List<ExcommunicationTile> periodExcommunicationTiles : excommunicationTiles.values()) {
					for (ExcommunicationTile excommunicationTile : periodExcommunicationTiles) {
						if (excommunicationTile.getModifier() != null) {
							excommunicationTile.getModifier().setEventClass();
						}
					}
				}
				return excommunicationTiles;
			} catch (IOException exception) {
				Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new EnumMap<>(Period.class);
		}
	}

	private static class PersonalBonusTilesBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = PersonalBonusTilesBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		PersonalBonusTilesBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		List<PersonalBonusTile> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return PersonalBonusTilesBuilder.GSON.fromJson(reader, new TypeToken<List<PersonalBonusTile>>()
				{
				}.getType());
			} catch (IOException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new ArrayList<>();
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

		Map<Integer, List<ResourceAmount>> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return CouncilPrivilegeRewardsBuilder.GSON.fromJson(reader, new TypeToken<Map<Integer, List<ResourceAmount>>>()
				{
				}.getType());
			} catch (IOException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new HashMap<>();
		}
	}
}
