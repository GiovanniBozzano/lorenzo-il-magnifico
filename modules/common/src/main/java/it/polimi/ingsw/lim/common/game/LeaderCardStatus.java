package it.polimi.ingsw.lim.common.game;

import java.io.Serializable;

public class LeaderCardStatus implements Serializable
{
	private final int index;
	private final boolean played;
	private final boolean activated;

	public LeaderCardStatus(int index, boolean played, boolean activated)
	{
		this.index = index;
		this.played = played;
		this.activated = activated;
	}

	public int getIndex()
	{
		return this.index;
	}

	public boolean isPlayed()
	{
		return this.played;
	}

	public boolean isActivated()
	{
		return this.activated;
	}
}
