package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.ActionInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Connection
{
	private String username;
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();
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
			if (connection.getUsername() != null && (connection.getPlayer() == null || connection.getPlayer().isOnline())) {
				connection.sendChatMessage(text);
			}
		}
	}

	public void disconnect(boolean flag, String message)
	{
		this.heartbeat.shutdownNow();
		Server.getInstance().getConnections().remove(this);
		if (this.player != null) {
			this.player.setOnline(false);
		}
		Room room = Room.getPlayerRoom(this);
		if (room != null) {
			room.handlePlayerDisconnection(this);
		}
	}

	public abstract void sendHeartbeat();

	public abstract void sendRoomEntryOther(String name);

	public abstract void sendRoomExitOther(String name);

	public abstract void sendRoomTimer(int timer);

	public abstract void sendDisconnectionLogMessage(String text);

	public abstract void sendChatMessage(String text);

	public abstract void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, List<ResourceAmount>> councilPalaceRewards, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex);

	public abstract void sendGameLogMessage(String text);

	public abstract void sendGameTimer(int timer);

	public abstract void sendGameDisconnectionOther(int playerIndex);

	public abstract void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles);

	public abstract void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex);

	public abstract void sendGamePersonalBonusTileChosen(int choicePlayerIndex);

	public abstract void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards);

	public abstract void sendGameLeaderCardChosen();

	public abstract void sendGameExcommunicationChoiceRequest(Period period);

	public abstract void sendGameExoommunicationChoiceOther();

	public abstract void sendGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, Map<ActionType, List<AvailableAction>> availableActions);

	public abstract void sendGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, ExpectedAction expectedAction);

	public abstract void sendGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, int turnPlayerIndex);

	public abstract void sendGameEnded(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord);

	public void handleRoomTimerRequest()
	{
		Room room = Room.getPlayerRoom(this);
		if (room != null) {
			this.sendRoomTimer(room.getTimer());
		}
	}

	public void handleChatMessage(String text)
	{
		for (Connection otherConnection : this.player.getRoom().getPlayers()) {
			if (otherConnection != this && (otherConnection.getPlayer() == null || otherConnection.getPlayer().isOnline())) {
				otherConnection.sendChatMessage("[" + this.getUsername() + "]: " + text);
			}
		}
	}

	public void handleGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		this.player.getRoom().getGameHandler().receivePersonalBonusTileChoice(this.getPlayer(), personalBonusTileIndex);
	}

	public void handleGameLeaderCardPlayerChoice(int leaderCardIndex) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		this.player.getRoom().getGameHandler().receiveLeaderCardChoice(this.getPlayer(), leaderCardIndex);
	}

	public void handleGameExcommunicationPlayerChoice(boolean excommunicated) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		this.player.getRoom().getGameHandler().receiveExcommunicationChoice(this.getPlayer(), excommunicated);
	}

	public void handleGameAction(ActionInformations action) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		this.player.getRoom().getGameHandler().receiveAction(this.getPlayer(), action);
	}

	public void handleGoodGame(int playerIndex) throws GameActionFailedException
	{
		if (this.player.getRoom().getGameHandler() == null) {
			return;
		}
		this.player.getRoom().getGameHandler().applyGoodGame(this.player, playerIndex);
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

	public Player getPlayer()
	{
		return this.player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}
}
