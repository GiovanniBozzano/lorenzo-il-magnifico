package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
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
			if (connection.getUsername() == null) {
				continue;
			}
			connection.sendChatMessage(text);
		}
	}

	public void disconnect(boolean flag, String message)
	{
		this.heartbeat.shutdownNow();
		Server.getInstance().getConnections().remove(this);
		if (this.playerHandler != null) {
			this.playerHandler.setOnline(false);
		}
		for (Room room : Server.getInstance().getRooms()) {
			room.removePlayer(this);
			if (room.getPlayers().isEmpty()) {
				Server.getInstance().getRooms().remove(room);
			} else {
				for (Connection connection : room.getPlayers()) {
					connection.sendRoomExitOther(this.username);
				}
			}
		}
	}

	public abstract void sendHeartbeat();

	public abstract void sendRoomEntryOther(String name);

	public abstract void sendRoomExitOther(String name);

	public abstract void sendRoomTimer(int timer);

	public abstract void sendLogMessage(String text);

	public abstract void sendChatMessage(String text);

	public abstract void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex);

	public abstract void sendGamePersonalBonusTileChoiceRequest(List<PersonalBonusTileInformations> personalBonusTilesInformations);

	public abstract void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex);

	public abstract void sendGamePersonalBonusTileChosen(int choicePlayerIndex);

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
			if (otherConnection != this) {
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
		boolean valid = false;
		for (PersonalBonusTileInformations personalBonusTileInformations : gameHandler.getPersonalBonusTilesInformations()) {
			if (personalBonusTileInformations.getIndex() == personalBonusTileIndex) {
				valid = true;
			}
		}
		if (!valid) {
			this.sendGamePersonalBonusTileChoiceRequest(gameHandler.getPersonalBonusTilesInformations());
			return;
		}
		for (Connection player : room.getPlayers()) {
			player.sendGamePersonalBonusTileChosen(this.getPlayerHandler().getIndex());
		}
		gameHandler.receivedPersonalBonusTileChoice();
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
