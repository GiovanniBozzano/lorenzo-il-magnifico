package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.ServerSettings;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Room
{
	private final RoomType roomType;
	private final List<Connection> players = new ArrayList<>();
	private int timer;
	private ScheduledExecutorService timerExecutor;
	private GameHandler gameHandler;

	public Room(RoomType roomType)
	{
		this.roomType = roomType;
		this.timer = ServerSettings.getInstance().getRoomTimer();
	}

	public void handlePlayerDisconnection(Connection player)
	{
		if (this.gameHandler == null) {
			this.removePlayer(player);
			if (this.players.isEmpty()) {
				Server.getInstance().getRooms().remove(this);
			} else {
				for (Connection otherPlayer : this.players) {
					if (otherPlayer != player && (otherPlayer.getPlayerHandler() == null || otherPlayer.getPlayerHandler().isOnline())) {
						otherPlayer.sendRoomExitOther(player.getUsername());
					}
				}
			}
			return;
		}
		player.getPlayerHandler().setOnline(false);
		for (Connection otherPlayer : this.players) {
			if (otherPlayer != player && otherPlayer.getPlayerHandler().isOnline()) {
				otherPlayer.sendGameDisconnectionOther(player.getPlayerHandler().getIndex());
			}
		}
		if (this.gameHandler.getPersonalBonusTileChoicePlayerIndex() == player.getPlayerHandler().getIndex()) {
			int personalBonusTileIndex = this.gameHandler.getAvailablePersonalBonusTiles().get(this.gameHandler.getRandomGenerator().nextInt(this.gameHandler.getAvailablePersonalBonusTiles().size()));
			player.getPlayerHandler().setPersonalBonusTile(PersonalBonusTile.fromIndex(personalBonusTileIndex));
			this.gameHandler.getAvailablePersonalBonusTiles().remove(this.gameHandler.getAvailablePersonalBonusTiles().indexOf(personalBonusTileIndex));
			for (Connection otherPlayer : this.getPlayers()) {
				if (otherPlayer != player && otherPlayer.getPlayerHandler().isOnline()) {
					player.sendGamePersonalBonusTileChosen(player.getPlayerHandler().getIndex());
				}
			}
			this.gameHandler.receivedPersonalBonusTileChoice();
			return;
		}
		if (this.gameHandler.getTurnPlayer() == player) {
			this.gameHandler.nextTurn();
		}
	}

	public void addPlayer(Connection player)
	{
		this.players.add(player);
		if (this.gameHandler == null) {
			if (this.players.size() > 1 && this.players.size() < this.getRoomType().getPlayersNumber() && this.timerExecutor == null) {
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
				}, 1, 1, TimeUnit.SECONDS);
			} else if (this.players.size() >= this.getRoomType().getPlayersNumber()) {
				this.startGame();
			}
		}
	}

	private void removePlayer(Connection player)
	{
		this.players.remove(player);
		if (this.gameHandler == null) {
			if (this.players.size() < 2) {
				if (this.timerExecutor != null) {
					this.timerExecutor.shutdownNow();
				}
				this.timerExecutor = null;
				this.timer = ServerSettings.getInstance().getRoomTimer();
			}
		}
	}

	private void startGame()
	{
		this.timerExecutor.shutdownNow();
		this.gameHandler = new GameHandler(this);
	}

	public static Room getPlayerRoom(Connection connection)
	{
		for (Room room : Server.getInstance().getRooms()) {
			if (room.getPlayers().contains(connection)) {
				return room;
			}
		}
		return null;
	}

	public static Room getPlayerRoom(String username)
	{
		for (Room room : Server.getInstance().getRooms()) {
			for (Connection player : room.getPlayers()) {
				if (player.getUsername().equals(username)) {
					return room;
				}
			}
		}
		return null;
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
}
