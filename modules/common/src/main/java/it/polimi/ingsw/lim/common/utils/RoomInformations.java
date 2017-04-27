package it.polimi.ingsw.lim.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoomInformations implements Serializable
{
	private final int id;
	private final String name;
	private final List<String> playerNames = new ArrayList<>();

	public RoomInformations(int id, String name, List<String> playerNames)
	{
		this.id = id;
		this.name = name;
		this.playerNames.addAll(playerNames);
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public List<String> getPlayerNames()
	{
		return this.playerNames;
	}
}
