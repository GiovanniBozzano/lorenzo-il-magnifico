package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.player.PlayerHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Connection
{
	private String username;
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();
	private PlayerHandler playerHandler;

	protected Connection()
	{
	}

	protected Connection(String username)
	{
		this.username = username;
	}

	protected Connection(String username, PlayerHandler playerHandler)
	{
		this.username = username;
		this.playerHandler = playerHandler;
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
			if (connection.getUsername() != null && (connection.getPlayerHandler() == null || connection.getPlayerHandler().isOnline())) {
				connection.sendChatMessage(text);
			}
		}
	}

	public void disconnect(boolean flag, String message)
	{
		this.heartbeat.shutdownNow();
		Server.getInstance().getConnections().remove(this);
		if (this.playerHandler != null) {
			this.playerHandler.setOnline(false);
		}
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		room.handlePlayerDisconnection(this);
	}

	public abstract void sendHeartbeat();

	public abstract void sendRoomEntryOther(String name);

	public abstract void sendRoomExitOther(String name);

	public abstract void sendRoomTimer(int timer);

	public abstract void sendLogMessage(String text);

	public abstract void sendChatMessage(String text);

	public abstract void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex);

	public abstract void sendGameDisconnectionOther(int playerIndex);

	public abstract void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles);

	public abstract void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex);

	public abstract void sendGamePersonalBonusTileChosen(int choicePlayerIndex);

	public abstract void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards);

	public abstract void sendGameLeaderCardChosen(int choicePlayerIndex);

	public abstract void sendGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<AvailableAction> availableActions);

	public abstract void sendGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, ExpectedAction expectedAction);

	public abstract void sendGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, int turnPlayerIndex);

	public void handleRoomTimerRequest()
	{
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		this.sendRoomTimer(room.getTimer());
	}

	public void handleChatMessage(String text)
	{
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		for (Connection otherConnection : room.getPlayers()) {
			if (otherConnection != this && (otherConnection.getPlayerHandler() == null || otherConnection.getPlayerHandler().isOnline())) {
				otherConnection.sendChatMessage("[" + this.getUsername() + "]: " + text);
			}
		}
	}

	public void handleGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex)
	{
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		if (gameHandler.getPersonalBonusTileChoicePlayerIndex() != this.playerHandler.getIndex()) {
			return;
		}
		if (gameHandler.getAvailablePersonalBonusTiles().contains(personalBonusTileIndex)) {
			this.sendGamePersonalBonusTileChoiceRequest(gameHandler.getAvailablePersonalBonusTiles());
			return;
		}
		this.playerHandler.setPersonalBonusTile(PersonalBonusTile.fromIndex(personalBonusTileIndex));
		gameHandler.getAvailablePersonalBonusTiles().remove(new Integer(personalBonusTileIndex));
		for (Connection player : room.getPlayers()) {
			if (player.getPlayerHandler().isOnline()) {
				player.sendGamePersonalBonusTileChosen(this.playerHandler.getIndex());
			}
		}
		gameHandler.receivedPersonalBonusTileChoice();
	}

	public void handleGameLeaderCardPlayerChoice(int leaderCardIndex)
	{
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		gameHandler.receivedLeaderCardChoice(this, leaderCardIndex);
	}

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	protected ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}

	public PlayerHandler getPlayerHandler()
	{
		return this.playerHandler;
	}

	public void setPlayerHandler(PlayerHandler playerHandler)
	{
		this.playerHandler = playerHandler;
	}
}
