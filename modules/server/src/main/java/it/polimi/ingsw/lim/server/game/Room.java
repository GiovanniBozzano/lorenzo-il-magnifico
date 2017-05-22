package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.server.ServerSettings;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Room
{
	private final int id;
	private final RoomType roomType;
	private final List<Connection> players = new ArrayList<>();
	private int timer;
	private ScheduledExecutorService timerExecutor;
	private boolean isStarted = false;

	public Room(int id, RoomType roomType)
	{
		this.id = id;
		this.roomType = roomType;
		this.timer = ServerSettings.getInstance().getRoomTimer();
	}

	public void addPlayer(Connection player)
	{
		this.players.add(player);
		if (this.players.size() > 1 && this.players.size() < this.getRoomType().getPlayersNumber() && this.timerExecutor == null) {
			this.timerExecutor = Executors.newSingleThreadScheduledExecutor();
			this.timerExecutor.scheduleWithFixedDelay(() -> {
				this.timer--;
				if (this.timer == 0) {
					this.timerExecutor.shutdownNow();
				} else {
					for (Connection roomPlayer : this.players) {
						roomPlayer.sendRoomTimer(this.timer);
					}
				}
			}, 1, 1, TimeUnit.SECONDS);
		} else if (this.players.size() >= this.getRoomType().getPlayersNumber()) {
			this.timerExecutor.shutdownNow();
		}
	}

	public void removePlayer(Connection player)
	{
		this.players.remove(player);
		if (this.players.size() < 2) {
			if (this.timerExecutor != null) {
				this.timerExecutor.shutdownNow();
			}
			this.timerExecutor = null;
			this.timer = ServerSettings.getInstance().getRoomTimer();
		}
	}

	public int getId()
	{
		return this.id;
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

	public boolean getIsStarted()
	{
		return this.isStarted;
	}
}
