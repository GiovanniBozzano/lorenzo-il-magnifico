package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;

public class Room
{
	private final int id;
	private final RoomType roomType;
	private final List<Connection> players = new ArrayList<>();
	private boolean isStarted = false;

	public Room(int id, RoomType roomType)
	{
		this.id = id;
		this.roomType = roomType;
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

	public boolean getIsStarted()
	{
		return this.isStarted;
	}
}
