package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.actions.ActionInformation;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

/**
 * <p>This class represents a user instance. It handles all the interactions
 * with a single user.
 */
public abstract class Connection
{
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();
	private String username;
	private Player player;

	protected Connection()
	{
	}

	protected Connection(String username)
	{
		this.username = username;
	}

	protected Connection(String username, Player player)
	{
		this.username = username;
		this.player = player;
	}

	public static void disconnectAll()
	{
		for (Connection connection : Server.getInstance().getConnections()) {
			connection.disconnect(true, null);
		}
		Server.getInstance().getConnections().clear();
	}

	public static void broadcastChatMessage(String text)
	{
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.username != null && (connection.player == null || connection.player.isOnline())) {
				connection.sendChatMessage(text);
			}
		}
	}

	public static void broadcastLogMessage(Room room, String text)
	{
		for (Connection connection : room.getPlayers()) {
			if (connection.player.isOnline()) {
				connection.sendGameLogMessage(text);
			}
		}
	}

	public static void broadcastLogMessageToOthers(Player player, String text)
	{
		for (Connection connection : player.getRoom().getPlayers()) {
			if (connection.player != player && connection.player.isOnline()) {
				connection.sendGameLogMessage(text);
			}
		}
	}

	protected abstract void sendChatMessage(String text);

	public void disconnect(@SuppressWarnings("squid:S1172") boolean flag, String message)
	{
		this.heartbeat.shutdownNow();
		Server.getInstance().getConnections().remove(this);
		if (this.player != null) {
			this.player.setOnline(false);
		}
		try {
			Room.getPlayerRoom(this).handlePlayerDisconnection(this);
		} catch (NoSuchElementException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public abstract void sendHeartbeat();

	public abstract void sendRoomEntryOther(String name);

	public abstract void sendRoomExitOther(String name);

	public abstract void sendRoomTimer(int timer);

	public abstract void sendDisconnectionLogMessage(String text);

	public abstract void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, List<ResourceAmount>> councilPalaceRewards, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex);

	public abstract void sendGameLogMessage(String text);

	public abstract void sendGameTimer(int timer);

	public abstract void sendGameDisconnectionOther(int playerIndex);

	public abstract void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles);

	public abstract void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex);

	public abstract void sendGamePersonalBonusTileChosen(int choicePlayerIndex);

	public abstract void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards);

	public abstract void sendGameExcommunicationChoiceRequest(Period period);

	public abstract void sendGameExoommunicationChoiceOther();

	public abstract void sendGameUpdate(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, Map<ActionType, List<Serializable>> availableActions);

	public abstract void sendGameUpdateExpectedAction(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, ExpectedAction expectedAction);

	public abstract void sendGameUpdateOtherTurn(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, int turnPlayerIndex);

	public abstract void sendGameEnded(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord);

	public void handleRoomTimerRequest()
	{
		try {
			this.sendRoomTimer(Room.getPlayerRoom(this).getTimer());
		} catch (NoSuchElementException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public void handleChatMessage(String text)
	{
		try {
			for (Connection otherConnection : Room.getPlayerRoom(this.username).getPlayers()) {
				if (otherConnection != this) {
					otherConnection.sendChatMessage('[' + this.username + "]: " + text);
				}
			}
		} catch (NoSuchElementException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public void handleGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		this.player.getRoom().getGameHandler().receivePersonalBonusTileChoice(this.player, personalBonusTileIndex);
	}

	public void handleGameLeaderCardPlayerChoice(int leaderCardIndex) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		this.player.getRoom().getGameHandler().receiveLeaderCardChoice(this.player, leaderCardIndex);
	}

	public void handleGameExcommunicationPlayerChoice(boolean excommunicated) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		this.player.getRoom().getGameHandler().receiveExcommunicationChoice(this.player, excommunicated);
	}

	public void handleGameAction(ActionInformation action) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		this.player.getRoom().getGameHandler().receiveAction(this.player, action);
	}

	public void handleGoodGame(int playerIndex) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		try {
			this.player.getRoom().getGameHandler().applyGoodGame(this.player, playerIndex);
		} catch (NoSuchElementException exception) {
			Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			throw new GameActionFailedException("The chosen Player does not exist");
		}
	}

	protected ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Player getPlayer()
	{
		return this.player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}
}
