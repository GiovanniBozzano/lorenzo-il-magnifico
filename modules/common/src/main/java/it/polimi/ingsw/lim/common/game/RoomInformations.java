package it.polimi.ingsw.lim.common.game;

import it.polimi.ingsw.lim.common.enums.RoomType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoomInformations implements Serializable
{
	private final RoomType roomType;
	private final List<String> playerNames = new ArrayList<>();

	public RoomInformations(RoomType roomType, List<String> playerNames)
	{
		this.roomType = roomType;
		this.playerNames.addAll(playerNames);
	}

	public RoomType getRoomType()
	{
		return this.roomType;
	}

	public List<String> getPlayerNames()
	{
		return this.playerNames;
	}
}
