package it.polimi.ingsw.lim.common.game.player;

import it.polimi.ingsw.lim.common.enums.Color;

import java.io.Serializable;

public class PlayerData implements Serializable
{
	private final String username;
	private final Color color;

	public PlayerData(String username, Color color)
	{
		this.username = username;
		this.color = color;
	}

	public String getUsername()
	{
		return this.username;
	}

	public Color getColor()
	{
		return this.color;
	}
}
