package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.server.game.bonus.Bonus;
import it.polimi.ingsw.lim.server.game.events.IEvent;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.List;

public interface IAction
{
	boolean isLegal();

	void apply();

	static void applyBonuses(List<Bonus<? extends IEvent>> bonuses, IEvent event)
	{
		for (Bonus bonus : bonuses) {
			bonus.call(event);
		}
	}

	Connection getPlayer();
}
