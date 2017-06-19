package it.polimi.ingsw.lim.server.utils;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.game.actions.*;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.database.Database;
import it.polimi.ingsw.lim.server.enums.Command;
import it.polimi.ingsw.lim.server.enums.QueryRead;
import it.polimi.ingsw.lim.server.enums.QueryValueType;
import it.polimi.ingsw.lim.server.enums.QueryWrite;
import it.polimi.ingsw.lim.server.game.actions.*;
import it.polimi.ingsw.lim.server.game.cards.CardsHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.gui.ControllerMain;
import it.polimi.ingsw.lim.server.network.Connection;
import javafx.application.Platform;

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
	public static final Map<ActionType, IActionTransformer> ACTIONS_TRANSFORMERS = new EnumMap<>(ActionType.class);

	static {
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER, (actionInformations, player) -> new ActionChooseLorenzoDeMediciLeader(((ActionInformationsChooseLorenzoDeMediciLeader) actionInformations).getLeaderCardIndex(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE, (actionInformations, player) -> new ActionChooseRewardCouncilPrivilege(((ActionInformationsChooseRewardCouncilPrivilege) actionInformations).getCouncilPalaceRewardIndexes(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_GET_DEVELOPMENT_CARD, (actionInformations, player) -> new ActionChooseRewardGetDevelopmentCard(((ActionInformationsChooseRewardGetDevelopmentCard) actionInformations).getServants(), ((ActionInformationsChooseRewardGetDevelopmentCard) actionInformations).getCardType(), ((ActionInformationsChooseRewardGetDevelopmentCard) actionInformations).getRow(), ((ActionInformationsChooseRewardGetDevelopmentCard) actionInformations).getInstantRewardRow(), ((ActionInformationsChooseRewardGetDevelopmentCard) actionInformations).getInstantDiscountChoice(), ((ActionInformationsChooseRewardGetDevelopmentCard) actionInformations).getDiscountChoice(), ((ActionInformationsChooseRewardGetDevelopmentCard) actionInformations).getResourceCostOption(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_HARVEST, (actionInformations, player) -> new ActionChooseRewardHarvest(((ActionInformationsChooseRewardHarvest) actionInformations).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_PRODUCTION, (actionInformations, player) -> new ActionChooseRewardProduction(((ActionInformationsChooseRewardProduction) actionInformations).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER, (actionInformations, player) -> new ActionChooseRewardTemporaryModifier(((ActionInformationsChooseRewardTemporaryModifier) actionInformations).getFamilyMemberType(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.COUNCIL_PALACE, (actionInformations, player) -> new ActionCouncilPalace(((ActionInformationsCouncilPalace) actionInformations).getFamilyMemberType(), ((ActionInformationsCouncilPalace) actionInformations).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.GET_DEVELOPMENT_CARD, (actionInformations, player) -> new ActionGetDevelopmentCard(((ActionInformationsGetDevelopmentCard) actionInformations).getFamilyMemberType(), ((ActionInformationsGetDevelopmentCard) actionInformations).getServants(), ((ActionInformationsGetDevelopmentCard) actionInformations).getCardType(), ((ActionInformationsGetDevelopmentCard) actionInformations).getRow(), ((ActionInformationsGetDevelopmentCard) actionInformations).getDiscountChoice(), ((ActionInformationsGetDevelopmentCard) actionInformations).getResourceCostOption(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.HARVEST_START, (actionInformations, player) -> new ActionHarvestStart(((ActionInformationsHarvestStart) actionInformations).getFamilyMemberType(), ((ActionInformationsHarvestStart) actionInformations).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.LEADER_ACTIVATION, (actionInformations, player) -> new ActionLeaderActivation(((ActionInformationsLeaderActivation) actionInformations).getLeaderCardIndex(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.LEADER_DISCARD, (actionInformations, player) -> new ActionLeaderDiscard(((ActionInformationsLeaderDiscard) actionInformations).getLeaderCardIndex(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.LEADER_PLAY, (actionInformations, player) -> new ActionLeaderPlay(((ActionInformationsLeaderPlay) actionInformations).getLeaderCardIndex(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.MARKET, (actionInformations, player) -> new ActionMarket(((ActionInformationsMarket) actionInformations).getFamilyMemberType(), ((ActionInformationsMarket) actionInformations).getServants(), ((ActionInformationsMarket) actionInformations).getMarketSlot(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.PASS_TURN, (actionInformations, player) -> new ActionPassTurn(player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.PRODUCTION_START, (actionInformations, player) -> new ActionProductionStart(((ActionInformationsProductionStart) actionInformations).getFamilyMemberType(), ((ActionInformationsProductionStart) actionInformations).getServants(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.PRODUCTION_TRADE, (actionInformations, player) -> new ActionProductionTrade(((ActionInformationsProductionTrade) actionInformations).getChosenDevelopmentCardsBuilding(), player));
		Utils.ACTIONS_TRANSFORMERS.put(ActionType.REFUSE_REWARD, (actionInformations, player) -> new ActionRefuseReward(player));
	}

	private Utils()
	{
	}

	/**
	 * <p>Checks online for the external IP address.
	 *
	 * @return a string representing the IP address if successful, otherwise
	 * null.
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
	 * <p>Prints a string to the log visible in the main screen.
	 *
	 * @param text the string to print.
	 */
	public static void displayToLog(String text)
	{
		if (!WindowFactory.getInstance().isWindowOpen(ControllerMain.class)) {
			return;
		}
		Platform.runLater(() -> {
			if (((ControllerMain) WindowFactory.getInstance().getCurrentWindow()).getLogTextArea().getText().length() < 1) {
				((ControllerMain) WindowFactory.getInstance().getCurrentWindow()).getLogTextArea().appendText(text);
			} else {
				((ControllerMain) WindowFactory.getInstance().getCurrentWindow()).getLogTextArea().appendText("\n" + text);
			}
		});
	}

	/**
	 * <p>Executes the given command.
	 *
	 * @param command the command to execute.
	 */
	public static void executeCommand(String command)
	{
		Utils.displayToLog("[Command]: " + command);
		String commandType = command;
		String commandArguments = null;
		if (command.contains(" ")) {
			commandType = commandType.substring(0, commandType.indexOf(' '));
			commandArguments = command.replaceAll(commandType + " ", "");
		}
		try {
			switch (Command.valueOf(commandType.toUpperCase(Locale.ENGLISH))) {
				case SAY:
					Command.handleSayCommand(commandArguments);
					break;
				case KICK:
					Command.handleKickCommand(commandArguments);
					break;
				default:
			}
		} catch (IllegalArgumentException exception) {
			Server.getDebugger().log(Level.INFO, "Command does not exist.", exception);
			Utils.displayToLog("Command does not exist.");
		}
	}

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
		try (ResultSet resultSet = Utils.sqlRead(QueryRead.GET_PASSWORD_AND_SALT_WITH_USERNAME, queryArguments)) {
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
		try (ResultSet resultSet = Utils.sqlRead(QueryRead.CHECK_EXISTING_USERNAME, queryArguments)) {
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
					Utils.sqlWrite(QueryWrite.INSERT_USERNAME_AND_PASSWORD_AND_SALT, queryArguments);
				} catch (SQLException exception) {
					Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				}
			});
		} catch (SQLException | NoSuchAlgorithmException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			throw new AuthenticationFailedException("Server error.");
		}
	}

	public static <T extends DevelopmentCard> Map<Period, List<T>> deepCopyDevelopmentCards(Map<Period, List<T>> original)
	{
		Map<Period, List<T>> copy = new HashMap<>();
		for (Entry<Period, List<T>> entry : original.entrySet()) {
			List<T> developmentCards = new ArrayList<>();
			developmentCards.addAll(entry.getValue());
			copy.put(entry.getKey(), developmentCards);
		}
		return copy;
	}

	public static List<LeaderCard> deepCopyLeaderCards(List<LeaderCard> original)
	{
		List<LeaderCard> copy = new ArrayList<>();
		copy.addAll(original);
		return copy;
	}

	public static LeaderCard getLeaderCardFromIndex(int leaderCardIndex)
	{
		for (LeaderCard leaderCard : CardsHandler.LEADER_CARDS) {
			if (leaderCard.getIndex() == leaderCardIndex) {
				return leaderCard;
			}
		}
		return null;
	}

	/**
	 * <p>Executes a read query on the database with a prepared statement.
	 *
	 * @param query the query to execute.
	 * @param queryArguments the arguments to fill the statement.
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
	 * <p>Executes a write query on the database with a prepared statement.
	 *
	 * @param query the query to execute.
	 * @param queryArguments the arguments to use to fill the prepared
	 * statement.
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
	 * <p>Fills a prepared statement with the given arguments.
	 *
	 * @param preparedStatement the prepared statement to fill.
	 * @param queryArguments the arguments to use to fill the prepared
	 * statement.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void fillStatement(PreparedStatement preparedStatement, List<QueryArgument> queryArguments) throws SQLException
	{
		if (queryArguments == null) {
			return;
		}
		for (int index = 0; index < queryArguments.size(); index++) {
			QueryArgument queryArgument = queryArguments.get(index);
			switch (queryArguments.get(index).getValueType()) {
				case INTEGER:
					Utils.setStatementInteger(preparedStatement, queryArgument, index);
					break;
				case LONG:
					Utils.setStatementLong(preparedStatement, queryArgument, index);
					break;
				case FLOAT:
					Utils.setStatementFloat(preparedStatement, queryArgument, index);
					break;
				case DOUBLE:
					Utils.setStatementDouble(preparedStatement, queryArgument, index);
					break;
				case STRING:
					Utils.setStatementString(preparedStatement, queryArgument, index);
					break;
				case BYTES:
					Utils.setStatementBytes(preparedStatement, queryArgument, index);
					break;
				default:
			}
		}
	}

	/**
	 * <p>Sets the given prepared statement argument to the given integer.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
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
	 * <p>Sets the given prepared statement argument to the given long.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
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
	 * <p>Sets the given prepared statement argument to the given float.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
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
	 * <p>Sets the given prepared statement argument to the given double.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
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
	 * <p>Sets the given prepared statement argument to the given string.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
	 *
	 * @throws SQLException if the process was not successful.
	 */
	private static void setStatementString(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException
	{
		preparedStatement.setString(index + 1, (String) queryArgument.getValue());
	}

	/**
	 * <p>Sets the given prepared statement argument to the given bytes array.
	 *
	 * @param preparedStatement the prepared statement to set.
	 * @param queryArgument the argument to use to set the prepared statement.
	 * @param index the index of the parepared statement argument to set.
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
	 * <p>Uses SHA-512 to encrypt a string with the given salt.
	 *
	 * @param text the string to encrypt.
	 * @param salt the salt to use.
	 *
	 * @return the encrypted string.
	 *
	 * @throws NoSuchAlgorithmException if the process was not successful.
	 */
	public static String sha512Encrypt(String text, byte[] salt) throws NoSuchAlgorithmException
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
	 * <p>Generates a random salt made as a 16 bytes array.
	 *
	 * @return the generated salt.
	 *
	 * @throws NoSuchAlgorithmException if the process was not successful.
	 */
	public static byte[] getSalt() throws NoSuchAlgorithmException
	{
		byte[] salt = new byte[16];
		SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);
		return salt;
	}
}
