package it.polimi.ingsw.lim.server.utils;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.*;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformation;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformation;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.database.Database;
import it.polimi.ingsw.lim.server.enums.*;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionReward;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.actions.*;
import it.polimi.ingsw.lim.server.game.board.ExcommunicationTile;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.*;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

public class Utils
{
	public static final String SCENE_MAIN = "/fxml/SceneMain.fxml";
	public static final String SCENE_START = "/fxml/SceneStart.fxml";
	private static final Map<Command, ICommandHandler> COMMAND_HANDLERS = new EnumMap<>(Command.class);
	private static final Map<QueryValueType, IQueryArgumentFiller> QUERY_ARGUMENTS_FILLERS = new EnumMap<>(QueryValueType.class);
	private static final Map<ActionType, IActionTransformer> ACTIONS_TRANSFORMERS = new EnumMap<>(ActionType.class);

	static {
		Utils.COMMAND_HANDLERS.put(Command.KICK, Command::handleKickCommand);
		Utils.COMMAND_HANDLERS.put(Command.SAY, Command::handleSayCommand);
	}

	static {
		Utils.QUERY_ARGUMENTS_FILLERS.put(QueryValueType.INTEGER, Utils::setStatementInteger);
		Utils.QUERY_ARGUMENTS_FILLERS.put(QueryValueType.LONG, Utils::setStatementLong);
		Utils.QUERY_ARGUMENTS_FILLERS.put(QueryValueType.FLOAT, Utils::setStatementFloat);
		Utils.QUERY_ARGUMENTS_FILLERS.put(QueryValueType.DOUBLE, Utils::setStatementDouble);
		Utils.QUERY_ARGUMENTS_FILLERS.put(QueryValueType.STRING, Utils::setStatementString);
		Utils.QUERY_ARGUMENTS_FILLERS.put(QueryValueType.BYTES, Utils::setStatementBytes);
	}

	static {
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER, (actionInformation, player) -> new ActionChooseLorenzoDeMediciLeader(((ActionInformationChooseLorenzoDeMediciLeader) actionInformation).getLeaderCardIndex(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE, (actionInformation, player) -> new ActionChooseRewardCouncilPrivilege(((ActionInformationChooseRewardCouncilPrivilege) actionInformation).getCouncilPrivilegeRewardIndexes(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD, (actionInformation, player) -> new ActionChooseRewardPickDevelopmentCard(((ActionInformationChooseRewardPickDevelopmentCard) actionInformation).getServants(), ((ActionInformationChooseRewardPickDevelopmentCard) actionInformation).getCardType(), ((ActionInformationChooseRewardPickDevelopmentCard) actionInformation).getRow(), ((ActionInformationChooseRewardPickDevelopmentCard) actionInformation).getInstantRewardRow(), ((ActionInformationChooseRewardPickDevelopmentCard) actionInformation).getInstantDiscountChoice(), ((ActionInformationChooseRewardPickDevelopmentCard) actionInformation).getDiscountChoice(), ((ActionInformationChooseRewardPickDevelopmentCard) actionInformation).getResourceCostOption(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_HARVEST, (actionInformation, player) -> new ActionChooseRewardHarvest(((ActionInformationChooseRewardHarvest) actionInformation).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_PRODUCTION_START, (actionInformation, player) -> new ActionChooseRewardProductionStart(((ActionInformationChooseRewardProductionStart) actionInformation).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER, (actionInformation, player) -> new ActionChooseRewardTemporaryModifier(((ActionInformationChooseRewardTemporaryModifier) actionInformation).getFamilyMemberType(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.COUNCIL_PALACE, (actionInformation, player) -> new ActionCouncilPalace(((ActionInformationCouncilPalace) actionInformation).getFamilyMemberType(), ((ActionInformationCouncilPalace) actionInformation).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.PICK_DEVELOPMENT_CARD, (actionInformation, player) -> new ActionPickDevelopmentCard(((ActionInformationPickDevelopmentCard) actionInformation).getFamilyMemberType(), ((ActionInformationPickDevelopmentCard) actionInformation).getServants(), ((ActionInformationPickDevelopmentCard) actionInformation).getCardType(), ((ActionInformationPickDevelopmentCard) actionInformation).getRow(), ((ActionInformationPickDevelopmentCard) actionInformation).getDiscountChoice(), ((ActionInformationPickDevelopmentCard) actionInformation).getResourceCostOption(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.HARVEST, (actionInformation, player) -> new ActionHarvest(((ActionInformationHarvest) actionInformation).getFamilyMemberType(), ((ActionInformationHarvest) actionInformation).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.LEADER_ACTIVATE, (actionInformation, player) -> new ActionLeaderActivate(((ActionInformationLeaderActivate) actionInformation).getLeaderCardIndex(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.LEADER_DISCARD, (actionInformation, player) -> new ActionLeaderDiscard(((ActionInformationLeaderDiscard) actionInformation).getLeaderCardIndex(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.LEADER_PLAY, (actionInformation, player) -> new ActionLeaderPlay(((ActionInformationLeaderPlay) actionInformation).getLeaderCardIndex(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.MARKET, (actionInformation, player) -> new ActionMarket(((ActionInformationMarket) actionInformation).getFamilyMemberType(), ((ActionInformationMarket) actionInformation).getServants(), ((ActionInformationMarket) actionInformation).getMarketSlot(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.PASS_TURN, (actionInformation, player) -> new ActionPassTurn(player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.PRODUCTION_START, (actionInformation, player) -> new ActionProductionStart(((ActionInformationProductionStart) actionInformation).getFamilyMemberType(), ((ActionInformationProductionStart) actionInformation).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.PRODUCTION_TRADE, (actionInformation, player) -> new ActionProductionTrade(((ActionInformationProductionTrade) actionInformation).getChosenDevelopmentCardsBuilding(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.REFUSE_REWARD, (actionInformation, player) -> new ActionRefuseReward(player));
	}

	private Utils()
	{
	}

	/**
	 * <p>Executes the given {@link Command}.
	 *
	 * @param command the {@link Command} to execute.
	 */
	public static void executeCommand(String command)
	{
		Server.getInstance().getInterfaceHandler().displayToLog("[Command]: " + command);
		String commandType = command;
		String commandArguments = null;
		if (command.contains(" ")) {
			commandType = commandType.substring(0, commandType.indexOf(' '));
			commandArguments = command.replaceAll(commandType + ' ', "");
		}
		try {
			Utils.COMMAND_HANDLERS.get(Command.valueOf(commandType.toUpperCase(Locale.ENGLISH))).execute(commandArguments);
		} catch (IllegalArgumentException exception) {
			Server.getDebugger().log(Level.OFF, "Command does not exist.", exception);
			Server.getInstance().getInterfaceHandler().displayToLog("Command does not exist.");
		} finally {
			Server.getInstance().getInterfaceHandler().handleCommandExecuted();
		}
	}

	/**
	 * Checks whether the authentication is valid or not.
	 *
	 * @param version the client version of the player.
	 * @param username the player username.
	 * @param password the player password.
	 *
	 * @throws AuthenticationFailedException if the authentication has failed.
	 */
	public static void checkLogin(String version, String username, String password) throws AuthenticationFailedException
	{
		if (!version.equals(CommonUtils.VERSION)) {
			throw new AuthenticationFailedException("Client version not compatible with the Server.");
		}
		if (!username.matches(CommonUtils.REGEX_USERNAME)) {
			throw new AuthenticationFailedException("Incorrect username.");
		}
		String decryptedPassword = CommonUtils.decrypt(password);
		if (decryptedPassword == null || decryptedPassword.length() < 1) {
			throw new AuthenticationFailedException("Incorrect password.");
		}
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getUsername() != null && connection.getUsername().equals(username)) {
				throw new AuthenticationFailedException("Already logged in.");
			}
		}
		List<QueryArgument> queryArguments = new ArrayList<>();
		queryArguments.add(new QueryArgument(QueryValueType.STRING, username));
		try (ResultSet resultSet = Utils.sqlRead(QueryRead.CHECK_PLAYER_PASSWORD, queryArguments)) {
			if (!resultSet.next()) {
				resultSet.getStatement().close();
				throw new AuthenticationFailedException("Incorrect username.");
			}
			if (!Utils.sha512Encrypt(decryptedPassword, resultSet.getBytes(Database.TABLE_PLAYERS_COLUMN_SALT)).equals(resultSet.getString(Database.TABLE_PLAYERS_COLUMN_PASSWORD))) {
				resultSet.getStatement().close();
				throw new AuthenticationFailedException("Incorrect password.");
			}
			resultSet.getStatement().close();
		} catch (SQLException | NoSuchAlgorithmException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			throw new AuthenticationFailedException("Server error.");
		}
	}

	/**
	 * Checks whether the registration is valid or not.
	 *
	 * @param version the client version of the player.
	 * @param username the player username.
	 * @param password the player password.
	 *
	 * @throws AuthenticationFailedException if the authentication has failed.
	 */
	public static void checkRegistration(String version, String username, String password) throws AuthenticationFailedException
	{
		if (!version.equals(CommonUtils.VERSION)) {
			throw new AuthenticationFailedException("Client version not compatible with the Server.");
		}
		if (!username.matches(CommonUtils.REGEX_USERNAME)) {
			throw new AuthenticationFailedException("Incorrect username.");
		}
		String decryptedPassword = CommonUtils.decrypt(password);
		if (decryptedPassword == null || decryptedPassword.length() < 1) {
			throw new AuthenticationFailedException("Incorrect password.");
		}
		List<QueryArgument> queryArguments = new ArrayList<>();
		queryArguments.add(new QueryArgument(QueryValueType.STRING, username));
		try (ResultSet resultSet = Utils.sqlRead(QueryRead.CHECK_EXISTING_PLAYER_USERNAME, queryArguments)) {
			if (resultSet.next()) {
				resultSet.getStatement().close();
				throw new AuthenticationFailedException("Username already taken.");
			}
			resultSet.getStatement().close();
			byte[] salt = Utils.getSalt();
			String encryptedPassword = Utils.sha512Encrypt(decryptedPassword, salt);
			queryArguments.clear();
			queryArguments.add(new QueryArgument(QueryValueType.STRING, username));
			queryArguments.add(new QueryArgument(QueryValueType.STRING, encryptedPassword));
			queryArguments.add(new QueryArgument(QueryValueType.BYTES, salt));
			Server.getInstance().getDatabaseSaver().execute(() -> {
				try {
					Utils.sqlWrite(QueryWrite.REGISTER_PLAYER, queryArguments);
				} catch (SQLException exception) {
					Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				}
			});
		} catch (SQLException | NoSuchAlgorithmException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			throw new AuthenticationFailedException("Server error.");
		}
	}

	/**
	 * Fills the basic game information for the loggin in player.
	 *
	 * @return an object representing basic game information.
	 */
	public static AuthenticationInformation fillAuthenticationInformation()
	{
		Map<Integer, DevelopmentCardBuildingInformation> developmentCardsBuildingsInformation = new HashMap<>();
		Map<Integer, DevelopmentCardCharacterInformation> developmentCardsCharacterInformation = new HashMap<>();
		Map<Integer, DevelopmentCardTerritoryInformation> developmentsCardTerritoryInformation = new HashMap<>();
		Map<Integer, DevelopmentCardVentureInformation> developmentCardsVentureInformation = new HashMap<>();
		for (Period period : Period.values()) {
			for (DevelopmentCardBuilding developmentCardBuilding : CardsHandler.getDevelopmentCardsBuilding().get(period)) {
				developmentCardsBuildingsInformation.put(developmentCardBuilding.getIndex(), developmentCardBuilding.getInformation());
			}
			for (DevelopmentCardCharacter developmentCardCharacter : CardsHandler.getDevelopmentCardsCharacter().get(period)) {
				developmentCardsCharacterInformation.put(developmentCardCharacter.getIndex(), developmentCardCharacter.getInformation());
			}
			for (DevelopmentCardTerritory developmentCardTerritory : CardsHandler.getDevelopmentCardsTerritory().get(period)) {
				developmentsCardTerritoryInformation.put(developmentCardTerritory.getIndex(), developmentCardTerritory.getInformation());
			}
			for (DevelopmentCardVenture developmentCardVenture : CardsHandler.getDevelopmentCardsVenture().get(period)) {
				developmentCardsVentureInformation.put(developmentCardVenture.getIndex(), developmentCardVenture.getInformation());
			}
		}
		Map<Integer, LeaderCardInformation> leaderCardsInformation = new HashMap<>();
		for (LeaderCard leaderCard : CardsHandler.getLeaderCards()) {
			leaderCardsInformation.put(leaderCard.getIndex(), leaderCard.getInformation());
		}
		Map<Integer, ExcommunicationTileInformation> excommunicationTilesInformation = new HashMap<>();
		for (ExcommunicationTile excommunicationTile : ExcommunicationTile.values()) {
			excommunicationTilesInformation.put(excommunicationTile.getIndex(), new ExcommunicationTileInformation(excommunicationTile.getTexturePath(), excommunicationTile.getModifier().getDescription()));
		}
		Map<Integer, PersonalBonusTileInformation> personalBonusTilesInformation = new HashMap<>();
		for (PersonalBonusTile personalBonusTile : PersonalBonusTile.values()) {
			personalBonusTilesInformation.put(personalBonusTile.getIndex(), new PersonalBonusTileInformation(personalBonusTile.getTexturePath(), personalBonusTile.getPlayerBoardTexturePath(), personalBonusTile.getProductionActivationCost(), personalBonusTile.getProductionInstantResources(), personalBonusTile.getHarvestActivationCost(), personalBonusTile.getHarvestInstantResources()));
		}
		AuthenticationInformation authenticationInformation = new AuthenticationInformation();
		authenticationInformation.setDevelopmentCardsBuildingInformation(developmentCardsBuildingsInformation);
		authenticationInformation.setDevelopmentCardsCharacterInformation(developmentCardsCharacterInformation);
		authenticationInformation.setDevelopmentCardsTerritoryInformation(developmentsCardTerritoryInformation);
		authenticationInformation.setDevelopmentCardsVentureInformation(developmentCardsVentureInformation);
		authenticationInformation.setLeaderCardsInformation(leaderCardsInformation);
		authenticationInformation.setExcommunicationTilesInformation(excommunicationTilesInformation);
		authenticationInformation.setPersonalBonusTilesInformation(personalBonusTilesInformation);
		return authenticationInformation;
	}

	/**
	 * Creates a new copy of the given {@link Period} {@link DevelopmentCard}s
	 * {@link Map}.
	 *
	 * @param original the original {@link Period} {@link DevelopmentCard}s
	 * {@link Map}.
	 *
	 * @return the newly created {@link Period} {@link DevelopmentCard}s {@link
	 * Map}.
	 */
	public static <T extends DevelopmentCard> Map<Period, List<T>> deepCopyDevelopmentCards(Map<Period, List<T>> original)
	{
		Map<Period, List<T>> copy = new EnumMap<>(Period.class);
		for (Entry<Period, List<T>> entry : original.entrySet()) {
			List<T> developmentCards = new ArrayList<>(entry.getValue());
			copy.put(entry.getKey(), developmentCards);
		}
		return copy;
	}

	/**
	 * Retrieves a {@link LeaderCard} from a given index, if it does exist.
	 *
	 * @param leaderCardIndex the {@link LeaderCard} index.
	 *
	 * @return the retrieved {@link LeaderCard} if it does exists, otherwise
	 * null.
	 */
	public static LeaderCard getLeaderCardFromIndex(int leaderCardIndex)
	{
		for (LeaderCard leaderCard : CardsHandler.getLeaderCards()) {
			if (leaderCard.getIndex() == leaderCardIndex) {
				return leaderCard;
			}
		}
		return null;
	}

	/**
	 * Checks whether the chosen discount is valid or not.
	 *
	 * @param player the {@link Player} who performs the action.
	 * @param cardType the {@link CardType} of the action.
	 * @param discountChoice the chosen discount.
	 * @param resourceCostOption the chosen {@link ResourceCostOption}.
	 *
	 * @throws GameActionFailedException if the discount is not valid.
	 */
	public static void checkValidDiscount(Player player, CardType cardType, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption) throws GameActionFailedException
	{
		if (resourceCostOption == null && !discountChoice.isEmpty()) {
			throw new GameActionFailedException("You chose a discount but the there is not cost");
		}
		Utils.checkValidDiscountInternal(player, cardType, discountChoice, resourceCostOption);
	}

	/**
	 * Checks whether the chosen discounts are valid or not.
	 *
	 * @param player the {@link Player} who performs the action.
	 * @param cardType the {@link CardType} of the action.
	 * @param instantDiscountChoice the chosen instant discount.
	 * @param discountChoice the chosen discount.
	 * @param resourceCostOption the chosen {@link ResourceCostOption}.
	 *
	 * @throws GameActionFailedException if the discounts are not valid.
	 */
	public static void checkValidDiscount(Player player, CardType cardType, List<ResourceAmount> instantDiscountChoice, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption) throws GameActionFailedException
	{
		if (resourceCostOption == null && (!instantDiscountChoice.isEmpty() || !discountChoice.isEmpty())) {
			throw new GameActionFailedException("You chose a discount but the there is not cost");
		}
		if (resourceCostOption != null && ((instantDiscountChoice.isEmpty() && !((ActionRewardPickDevelopmentCard) player.getCurrentActionReward()).getInstantDiscountChoices().isEmpty()) || (!instantDiscountChoice.isEmpty() && !((ActionRewardPickDevelopmentCard) player.getCurrentActionReward()).getInstantDiscountChoices().contains(instantDiscountChoice)))) {
			throw new GameActionFailedException("You had to choose a discount");
		}
		Utils.checkValidDiscountInternal(player, cardType, discountChoice, resourceCostOption);
	}

	/**
	 * Checks whether the chosen discount is valid or not.
	 *
	 * @param player the {@link Player} who performs the action.
	 * @param cardType the {@link CardType} of the action.
	 * @param discountChoice the chosen discount.
	 * @param resourceCostOption the chosen {@link ResourceCostOption}.
	 *
	 * @throws GameActionFailedException if the discount is not valid.
	 */
	private static void checkValidDiscountInternal(Player player, CardType cardType, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption) throws GameActionFailedException
	{
		if (resourceCostOption != null) {
			if (discountChoice.isEmpty()) {
				for (Modifier modifier : player.getActiveModifiers()) {
					if (modifier instanceof ModifierPickDevelopmentCard && ((ModifierPickDevelopmentCard) modifier).getCardType() == cardType && !((ModifierPickDevelopmentCard) modifier).getDiscountChoices().isEmpty()) {
						throw new GameActionFailedException("The discount you chose is not valid");
					}
				}
			} else {
				boolean validDiscountChoice = false;
				for (Modifier modifier : player.getActiveModifiers()) {
					if (modifier instanceof ModifierPickDevelopmentCard && ((ModifierPickDevelopmentCard) modifier).getCardType() == cardType && ((ModifierPickDevelopmentCard) modifier).getDiscountChoices().contains(discountChoice)) {
						validDiscountChoice = true;
					}
				}
				if (!validDiscountChoice) {
					throw new GameActionFailedException("The discount you chose is not valid");
				}
			}
		}
	}

	/**
	 * Checks whether the {@link Player} has available production cards and acts
	 * accordingly.
	 *
	 * @param player the {@link Player} who performs the action.
	 */
	public static void checkAvailableProductionCards(Player player)
	{
		Map<Integer, List<ResourceTradeOption>> availableCards = new HashMap<>();
		for (DevelopmentCardBuilding developmentCardBuilding : player.getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
			if (developmentCardBuilding.getActivationValue() <= player.getCurrentProductionValue()) {
				List<ResourceTradeOption> availableResourceTradeOptions = new ArrayList<>();
				for (ResourceTradeOption resourceTradeOption : developmentCardBuilding.getResourceTradeOptions()) {
					if (player.getPlayerResourceHandler().canAffordResources(resourceTradeOption.getEmployedResources())) {
						availableResourceTradeOptions.add(resourceTradeOption);
					}
				}
				if (!availableResourceTradeOptions.isEmpty()) {
					availableCards.put(developmentCardBuilding.getIndex(), availableResourceTradeOptions);
				}
			}
		}
		if (availableCards.isEmpty()) {
			if (Utils.sendCouncilPrivileges(player)) {
				return;
			}
			if (player.getRoom().getGameHandler().getCurrentPhase() == Phase.LEADER) {
				player.getRoom().getGameHandler().setExpectedAction(null);
				player.getRoom().getGameHandler().sendGameUpdate(player);
				return;
			}
			player.getRoom().getGameHandler().nextTurn();
			return;
		}
		player.getRoom().getGameHandler().sendGameUpdateExpectedAction(player, new ExpectedActionProductionTrade(availableCards));
	}

	/**
	 * <p>Subtracts a discount from a {@link ResourceAmount} {@link List}.
	 *
	 * @param resourceAmounts the original {@link ResourceAmount} {@link List}.
	 * @param discountChoice the discount to subtract.
	 */
	public static void subtractDiscount(List<ResourceAmount> resourceAmounts, List<ResourceAmount> discountChoice)
	{
		for (ResourceAmount effectiveResourceAmount : resourceAmounts) {
			for (ResourceAmount discountResourceAmount : discountChoice) {
				if (discountResourceAmount.getResourceType() == effectiveResourceAmount.getResourceType()) {
					effectiveResourceAmount.setAmount(effectiveResourceAmount.getAmount() - discountResourceAmount.getAmount());
				}
			}
		}
	}

	/**
	 * <p>Checks if the {@link Player} has new council privileges and acts
	 * accordingly.
	 *
	 * @param player the {@link Player} who performs the action.
	 *
	 * @return true if the {@link Player} has new council privileges, otherwise
	 * false.
	 */
	public static boolean sendCouncilPrivileges(Player player)
	{
		int councilPrivilegesCount = player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE) + player.getPlayerResourceHandler().getResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			player.getRoom().getGameHandler().setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
			player.getRoom().getGameHandler().sendGameUpdateExpectedAction(player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
			return true;
		}
		return false;
	}

	/**
	 * <p>Activates an {@link ActionReward} if it does exist.
	 *
	 * @param player the {@link Player} who performs the action.
	 * @param actionReward the {@link ActionReward} which has to be activated.
	 *
	 * @return true if the {@link ActionReward} exists, otherwise false.
	 */
	public static boolean sendActionReward(Player player, ActionReward actionReward)
	{
		if (actionReward != null && actionReward.getRequestedAction() != null) {
			player.setCurrentActionReward(actionReward);
			player.getRoom().getGameHandler().setExpectedAction(actionReward.getRequestedAction());
			player.getRoom().getGameHandler().sendGameUpdateExpectedAction(player, actionReward.createExpectedAction(player));
			return true;
		}
		return false;
	}

	/**
	 * <p>Activates the given {@link LeaderCard} accordingly.
	 *
	 * @param player the {@link Player} who performs the action.
	 * @param leaderCard the {@link LeaderCard} which has to be activated.
	 *
	 * @return true if the {@link Phase} is the action goes on, otherwise false.
	 */
	public static boolean activateLeaderCard(Player player, LeaderCard leaderCard)
	{
		if (leaderCard instanceof LeaderCardModifier) {
			player.getActiveModifiers().add(((LeaderCardModifier) leaderCard).getModifier());
		} else {
			((LeaderCardReward) leaderCard).setActivated(true);
			EventGainResources eventGainResources = new EventGainResources(player, ((LeaderCardReward) leaderCard).getReward().getResourceAmounts(), ResourcesSource.LEADER_CARDS);
			eventGainResources.applyModifiers(player.getActiveModifiers());
			player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
			if (Utils.sendActionReward(player, ((LeaderCardReward) leaderCard).getReward().getActionReward())) {
				return true;
			}
			if (Utils.sendCouncilPrivileges(player)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>Checks the current {@link Phase} and acts accordingly.
	 *
	 * @param player the {@link Player} who performs the action.
	 *
	 * @return true if the {@link Phase} is the leaders one, otherwise false.
	 */
	public static boolean checkLeaderPhase(Player player)
	{
		if (player.getRoom().getGameHandler().getCurrentPhase() == Phase.LEADER) {
			player.getPlayerResourceHandler().convertTemporaryResources();
			player.getRoom().getGameHandler().setExpectedAction(null);
			player.getRoom().getGameHandler().sendGameUpdate(player);
			return true;
		}
		return false;
	}

	/**
	 * <p>Executes a {@link QueryRead} on the database with a {@link
	 * PreparedStatement}.
	 *
	 * @param query the {@link QueryRead} to execute.
	 * @param queryArguments the {@link QueryArgument} {@link List} to fill the
	 * {@link PreparedStatement}.
	 *
	 * @return the rows returned by the query.
	 *
	 * @throws SQLException if the query was not successful.
	 */
	public static ResultSet sqlRead(QueryRead query, List<QueryArgument> queryArguments) throws SQLException
	{
		try {
			PreparedStatement preparedStatement = Server.getInstance().getDatabase().getConnection().prepareStatement(query.getText());
			Utils.fillStatement(preparedStatement, queryArguments);
			return preparedStatement.executeQuery();
		} catch (SQLException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			throw exception;
		}
	}

	/**
	 * <p>Executes a {@link QueryWrite} on the database with a {@link
	 * PreparedStatement}.
	 *
	 * @param query the {@link QueryWrite} to execute.
	 * @param queryArguments the {@link QueryArgument} {@link List} to use to
	 * fill the {@link PreparedStatement}.
	 *
	 * @throws SQLException if the query was not successful.
	 */
	public static void sqlWrite(QueryWrite query, List<QueryArgument> queryArguments) throws SQLException
	{
		try (PreparedStatement preparedStatement = Server.getInstance().getDatabase().getConnection().prepareStatement(query.getText())) {
			Utils.fillStatement(preparedStatement, queryArguments);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			throw exception;
		}
	}

	/**
	 * <p>Fills a {@link PreparedStatement} with the given {@link
	 * QueryArgument}s.
	 *
	 * @param preparedStatement the {@link PreparedStatement} to fill.
	 * @param queryArguments the {@link QueryArgument} to use to fill the {@link
	 * PreparedStatement}.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void fillStatement(PreparedStatement preparedStatement, List<QueryArgument> queryArguments) throws SQLException
	{
		if (queryArguments == null) {
			return;
		}
		for (int index = 0; index < queryArguments.size(); index++) {
			Utils.QUERY_ARGUMENTS_FILLERS.get(queryArguments.get(index).getValueType()).execute(preparedStatement, queryArguments.get(index), index);
		}
	}

	/**
	 * <p>Sets the given {@link PreparedStatement} {@link QueryArgument} to the
	 * given {@link Integer}.
	 *
	 * @param preparedStatement the {@link PreparedStatement} to set.
	 * @param queryArgument the {@link QueryArgument} to use to set the {@link
	 * PreparedStatement}.
	 * @param index the index of the {@link PreparedStatement} {@link
	 * QueryArgument} to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementInteger(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof Integer) {
			preparedStatement.setInt(index + 1, (Integer) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.INTEGER);
	}

	/**
	 * <p>Sets the given {@link PreparedStatement} {@link QueryArgument} to the
	 * given {@link Long}.
	 *
	 * @param preparedStatement the {@link PreparedStatement} to set.
	 * @param queryArgument the {@link QueryArgument} to use to set the {@link
	 * PreparedStatement}.
	 * @param index the index of the {@link PreparedStatement} {@link
	 * QueryArgument} to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementLong(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof Long) {
			preparedStatement.setLong(index + 1, (Long) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.BIGINT);
	}

	/**
	 * <p>Sets the given {@link PreparedStatement} {@link QueryArgument} to the
	 * given {@link Float}.
	 *
	 * @param preparedStatement the {@link PreparedStatement} to set.
	 * @param queryArgument the {@link QueryArgument} to use to set the {@link
	 * PreparedStatement}.
	 * @param index the index of the {@link PreparedStatement} {@link
	 * QueryArgument} to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementFloat(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof Float) {
			preparedStatement.setFloat(index + 1, (Float) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.FLOAT);
	}

	/**
	 * <p>Sets the given {@link PreparedStatement} {@link QueryArgument} to the
	 * given {@link Double}.
	 *
	 * @param preparedStatement the {@link PreparedStatement} to set.
	 * @param queryArgument the {@link QueryArgument} to use to set the {@link
	 * PreparedStatement}.
	 * @param index the index of the {@link PreparedStatement} {@link
	 * QueryArgument} to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementDouble(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof Double) {
			preparedStatement.setDouble(index + 1, (Double) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.DOUBLE);
	}

	/**
	 * <p>Sets the given {@link PreparedStatement} {@link QueryArgument} to the
	 * given {@link String}.
	 *
	 * @param preparedStatement the {@link PreparedStatement} to set.
	 * @param queryArgument the {@link QueryArgument} to use to set the {@link
	 * PreparedStatement}.
	 * @param index the index of the {@link PreparedStatement} {@link
	 * QueryArgument} to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementString(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		preparedStatement.setString(index + 1, (String) queryArgument.getValue());
	}

	/**
	 * <p>Sets the given {@link PreparedStatement} {@link QueryArgument} to the
	 * given {@link Byte} array array.
	 *
	 * @param preparedStatement the {@link PreparedStatement} to set.
	 * @param queryArgument the {@link QueryArgument} to use to set the {@link
	 * PreparedStatement}.
	 * @param index the index of the {@link PreparedStatement} {@link
	 * QueryArgument} to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementBytes(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		if (queryArgument.getValue() instanceof byte[]) {
			preparedStatement.setBytes(index + 1, (byte[]) queryArgument.getValue());
			return;
		}
		preparedStatement.setNull(index + 1, Types.BLOB);
	}

	/**
	 * <p>Uses SHA-512 to encrypt a {@link String} with the given salt.
	 *
	 * @param text the {@link String} to encrypt.
	 * @param salt the salt to use.
	 *
	 * @return the encrypted {@link String}.
	 *
	 * @throws NoSuchAlgorithmException if the process was not successful.
	 */
	private static String sha512Encrypt(String text, byte[] salt) throws NoSuchAlgorithmException
	{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
		messageDigest.update(salt);
		byte[] bytes = messageDigest.digest(text.getBytes());
		StringBuilder stringBuilder = new StringBuilder();
		for (byte currentByte : bytes) {
			stringBuilder.append(Integer.toString((currentByte & 0xFF) + 0x100, 16).substring(1));
		}
		return stringBuilder.toString();
	}

	/**
	 * <p>Checks online for the external IP address.
	 *
	 * @return a {@link String} representing the IP address if successful,
	 * otherwise null.
	 */
	public static String getExternalIpAddress()
	{
		try {
			URL myIP = new URL("http://checkip.amazonaws.com");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myIP.openStream()));
			return bufferedReader.readLine();
		} catch (IOException exception) {
			Server.getDebugger().log(Level.INFO, "Cannot retrieve IP address...", exception);
		}
		return null;
	}

	/**
	 * <p>Generates a random salt made as a 16 {@link Byte} array.
	 *
	 * @return the generated salt.
	 *
	 * @throws NoSuchAlgorithmException if the process was not successful.
	 */
	private static byte[] getSalt() throws NoSuchAlgorithmException
	{
		byte[] salt = new byte[16];
		SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);
		return salt;
	}

	public static Map<ActionType, IActionTransformer> getActionsTransformers()
	{
		return Utils.ACTIONS_TRANSFORMERS;
	}
}
