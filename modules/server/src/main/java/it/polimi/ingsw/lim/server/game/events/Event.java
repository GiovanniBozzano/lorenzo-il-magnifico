package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.List;

public abstract class Event
{
	private final Connection player;

	public Event(Connection player)
	{
		this.player = player;
	}

	public void applyModifiers(List<Modifier> modifiers)
	{
		for (Modifier modifier : modifiers) {
			modifier.call(this);
		}
	}

	public Connection getPlayer()
	{
		return this.player;
	}
}
