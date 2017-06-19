package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.util.List;

public abstract class Event
{
	private final Player player;

	Event(Player player)
	{
		this.player = player;
	}

	public void applyModifiers(List<Modifier> modifiers)
	{
		for (Modifier modifier : modifiers) {
			modifier.call(this);
		}
	}

	public Player getPlayer()
	{
		return this.player;
	}
}
