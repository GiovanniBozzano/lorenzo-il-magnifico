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

/**
 * <p>This class represents a game room. It is the container for both the
 * pre-game phase and the match.
 */
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

	/**
	 * <p>Retrieves a {@link Connection}'s {@link Room}.
	 *
	 * @param connection the given {@link Connection}.
	 *
	 * @return the requested {@link Room}.
	 *
	 * @throws NoSuchElementException if a {@link Room} containing this {@link
	 * Connection} does not exist.
	 */
	public static Room getPlayerRoom(Connection connection)
	{
		for (Room room : Server.getInstance().getRooms()) {
			if (room.players.contains(connection)) {
				return room;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * <p>Retrieves a {@link Connection}'s {@link Room} with the user's
	 * username.
	 *
	 * @param username the given username.
	 *
	 * @return the requested {@link Room}.
	 *
	 * @throws NoSuchElementException if a {@link Room} containing this username
	 * does not exist.
	 */
	public static Room getPlayerRoom(String username)
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

	/**
	 * <p>Handles a user disconnection accordingly, acting automatically if he
	 * is playing.
	 *
	 * @param connection the disconnecting user.
	 */
	public void handlePlayerDisconnection(Connection connection)
	{
		if (this.gameHandler == null) {
			this.removePlayer(connection);
			if (this.players.isEmpty()) {
				if (this.gameHandler != null) {
					this.gameHandler.getTimerExecutor().shutdownNow();
				}
				Server.getInstance().getRooms().remove(this);
			} else {
				for (Connection otherPlayer : this.players) {
					if (otherPlayer != connection && (otherPlayer.getPlayer() == null || otherPlayer.getPlayer().isOnline())) {
						otherPlayer.sendRoomExitOther(connection.getUsername());
					}
				}
			}
			return;
		}
		connection.getPlayer().setOnline(false);
		if (this.endGame) {
			return;
		}
		for (Connection otherPlayer : this.players) {
			if (otherPlayer != connection && otherPlayer.getPlayer().isOnline()) {
				otherPlayer.sendGameDisconnectionOther(connection.getPlayer().getIndex());
			}
		}
		if (this.gameHandler.getCurrentPeriod() == null && this.gameHandler.getCurrentRound() == null) {
			if (this.gameHandler.getPersonalBonusTileChoicePlayerTurnIndex() > 0 && this.gameHandler.getPersonalBonusTileChoicePlayerTurnIndex() < this.gameHandler.getTurnOrder().size() && this.gameHandler.getTurnOrder().get(this.gameHandler.getPersonalBonusTileChoicePlayerTurnIndex()).getIndex() == connection.getPlayer().getIndex()) {
				int personalBonusTileIndex = this.gameHandler.getAvailablePersonalBonusTiles().get(this.gameHandler.getRandomGenerator().nextInt(this.gameHandler.getAvailablePersonalBonusTiles().size()));
				this.gameHandler.applyPersonalBonusTileChoice(connection.getPlayer(), personalBonusTileIndex);
				return;
			}
			if (this.gameHandler.getLeaderCardsChoosingPlayers().contains(connection.getPlayer())) {
				int leaderCardIndex = this.gameHandler.getAvailableLeaderCards().get(connection.getPlayer()).get(this.gameHandler.getRandomGenerator().nextInt(this.gameHandler.getAvailableLeaderCards().get(connection.getPlayer()).size()));
				this.gameHandler.applyLeaderCardChoice(connection.getPlayer(), leaderCardIndex);
				return;
			}
			return;
		}
		connection.getPlayer().getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
		connection.getPlayer().getPlayerResourceHandler().getResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
		if (this.gameHandler.getExcommunicationChoosingPlayers().contains(connection.getPlayer())) {
			this.gameHandler.applyExcommunicationChoice(connection.getPlayer(), false);
			return;
		}
		if (this.gameHandler.getTurnPlayer() == connection.getPlayer()) {
			this.gameHandler.nextTurn();
		}
	}

	/**
	 * <p>Removes a {@link Connection} from this {@link Room} and destroys the
	 * {@link Room} if it empty.
	 *
	 * @param connection the {@link Connection} to remove.
	 */
	private void removePlayer(Connection connection)
	{
		this.players.remove(connection);
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

	/**
	 * <p>Adds a {@link Connection} to this {@link Room} and starts the match if
	 * needed.
	 *
	 * @param connection the {@link Connection} to add.
	 */
	public void addPlayer(Connection connection)
	{
		this.players.add(connection);
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

	/**
	 * <p>Starts the countdown to the match starting.
	 */
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

	/**
	 * <p>Starts the match.
	 */
	private void startGame()
	{
		if (this.timerExecutor != null) {
			this.timerExecutor.shutdownNow();
		}
		this.gameHandler = new GameHandler(this);
		this.gameHandler.start();
	}

	/**
	 * <p>Kills the services started in this {@link Room}.
	 */
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
