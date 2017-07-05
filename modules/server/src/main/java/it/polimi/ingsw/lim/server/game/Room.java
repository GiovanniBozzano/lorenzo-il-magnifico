package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.ServerSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Room
{
	private final RoomType roomType;
	private final List<Connection> players = new ArrayList<>();
	private ScheduledExecutorService timerExecutor;
	private int timer;
	private GameHandler gameHandler;
	private boolean endGame = false;

	public Room(RoomType roomType)
	{
		this.roomType = roomType;
		if (roomType == RoomType.NORMAL) {
			this.timer = ServerSettings.getInstance().getRoomTimer();
		} else {
			this.timer = ServerSettings.getInstance().getExtendedRoomTimer();
		}
	}

	public static Room getPlayerRoom(Connection connection) throws NoSuchElementException
	{
		for (Room room : Server.getInstance().getRooms()) {
			if (room.players.contains(connection)) {
				return room;
			}
		}
		throw new NoSuchElementException();
	}

	public static Room getPlayerRoom(String username) throws NoSuchElementException
	{
		for (Room room : Server.getInstance().getRooms()) {
			for (Connection player : room.players) {
				if (player.getUsername().equals(username)) {
					return room;
				}
			}
		}
		throw new NoSuchElementException();
	}

	public void handlePlayerDisconnection(Connection player)
	{
		if (this.gameHandler == null) {
			this.removePlayer(player);
			if (this.players.isEmpty()) {
				if (this.gameHandler != null) {
					this.gameHandler.getTimerExecutor().shutdownNow();
				}
				Server.getInstance().getRooms().remove(this);
			} else {
				for (Connection otherPlayer : this.players) {
					if (otherPlayer != player && (otherPlayer.getPlayer() == null || otherPlayer.getPlayer().isOnline())) {
						otherPlayer.sendRoomExitOther(player.getUsername());
					}
				}
			}
			return;
		}
		player.getPlayer().setOnline(false);
		if (this.endGame) {
			return;
		}
		for (Connection otherPlayer : this.players) {
			if (otherPlayer != player && otherPlayer.getPlayer().isOnline()) {
				otherPlayer.sendGameDisconnectionOther(player.getPlayer().getIndex());
			}
		}
		if (this.gameHandler.getCurrentPeriod() == null && this.gameHandler.getCurrentRound() == null) {
			if (this.gameHandler.getPersonalBonusTileChoicePlayerTurnIndex() > 0 && this.gameHandler.getPersonalBonusTileChoicePlayerTurnIndex() < this.gameHandler.getTurnOrder().size() && this.gameHandler.getTurnOrder().get(this.gameHandler.getPersonalBonusTileChoicePlayerTurnIndex()).getIndex() == player.getPlayer().getIndex()) {
				int personalBonusTileIndex = this.gameHandler.getAvailablePersonalBonusTiles().get(this.gameHandler.getRandomGenerator().nextInt(this.gameHandler.getAvailablePersonalBonusTiles().size()));
				this.gameHandler.applyPersonalBonusTileChoice(player.getPlayer(), personalBonusTileIndex);
				return;
			}
			if (this.gameHandler.getLeaderCardsChoosingPlayers().contains(player.getPlayer())) {
				int leaderCardIndex = this.gameHandler.getAvailableLeaderCards().get(player.getPlayer()).get(this.gameHandler.getRandomGenerator().nextInt(this.gameHandler.getAvailableLeaderCards().get(player.getPlayer()).size()));
				this.gameHandler.applyLeaderCardChoice(player.getPlayer(), leaderCardIndex);
				return;
			}
			return;
		}
		player.getPlayer().getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
		player.getPlayer().getPlayerResourceHandler().getResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
		if (this.gameHandler.getExcommunicationChoosingPlayers().contains(player.getPlayer())) {
			this.gameHandler.applyExcommunicationChoice(player.getPlayer(), false);
			return;
		}
		if (this.gameHandler.getTurnPlayer() == player.getPlayer()) {
			this.gameHandler.nextTurn();
		}
	}

	private void removePlayer(Connection player)
	{
		this.players.remove(player);
		if (this.roomType == RoomType.NORMAL) {
			if (this.gameHandler == null && this.players.size() < 2) {
				if (this.timerExecutor != null) {
					this.timerExecutor.shutdownNow();
				}
				this.timerExecutor = null;
				this.timer = ServerSettings.getInstance().getRoomTimer();
			}
		} else {
			if (this.gameHandler == null && this.players.size() < 5) {
				if (this.timerExecutor != null) {
					this.timerExecutor.shutdownNow();
				}
				this.timerExecutor = null;
				this.timer = ServerSettings.getInstance().getExtendedRoomTimer();
			}
		}
	}

	public void addPlayer(Connection player)
	{
		this.players.add(player);
		if (this.gameHandler == null) {
			if (this.roomType == RoomType.NORMAL) {
				if (this.players.size() > 1 && this.timerExecutor == null) {
					this.startTimer();
				}
			} else if (this.players.size() >= this.roomType.getPlayersNumber()) {
				this.startTimer();
			}
		}
	}

	private void startTimer()
	{
		this.timerExecutor = Executors.newSingleThreadScheduledExecutor();
		this.timerExecutor.scheduleWithFixedDelay(() -> {
			this.timer--;
			if (this.timer == 0) {
				this.startGame();
			} else {
				for (Connection roomPlayer : this.players) {
					roomPlayer.sendRoomTimer(this.timer);
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
	}

	private void startGame()
	{
		if (this.timerExecutor != null) {
			this.timerExecutor.shutdownNow();
		}
		this.gameHandler = new GameHandler(this);
		this.gameHandler.start();
	}

	public void dispose()
	{
		if (this.timerExecutor != null) {
			this.timerExecutor.shutdownNow();
		}
		if (this.gameHandler != null) {
			this.gameHandler.getTimerExecutor().shutdownNow();
		}
	}

	public RoomType getRoomType()
	{
		return this.roomType;
	}

	public List<Connection> getPlayers()
	{
		return this.players;
	}

	public int getTimer()
	{
		return this.timer;
	}

	public GameHandler getGameHandler()
	{
		return this.gameHandler;
	}

	public void setGameHandler(GameHandler gameHandler)
	{
		this.gameHandler = gameHandler;
	}

	public boolean isEndGame()
	{
		return this.endGame;
	}

	void setEndGame(boolean endGame)
	{
		this.endGame = endGame;
	}
}
