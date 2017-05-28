package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.List;

public interface IAction
{
	boolean isLegal();

	void apply();

	static void applyModifiers(List<Modifier<? extends Event>> modifiers, Event event)
	{
		for (Modifier modifier : modifiers) {
			modifier.call(event);
		}
	}

	Connection getPlayer();
}
