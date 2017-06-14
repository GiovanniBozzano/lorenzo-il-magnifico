package it.polimi.ingsw.lim.common.game.cards;

import java.io.Serializable;

public class LeaderCardStatus implements Serializable
{
	private final boolean played;
	private final boolean activated;

	public LeaderCardStatus(boolean played, boolean activated)
	{
		this.played = played;
		this.activated = activated;
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
