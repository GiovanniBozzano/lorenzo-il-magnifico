package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.bonus.Bonus;
import it.polimi.ingsw.lim.common.events.IEvent;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.List;

public interface IAction
{
	boolean isLegal();

	void apply();

	@SuppressWarnings("unchecked")
	static <T extends IEvent> List<Bonus<T>> getAppliedBonuses(Class<T> eventClass, List<Bonus<? extends IEvent>> bonuses)
	{
		List<Bonus<T>> appliedBonuses = new ArrayList<>();
		for (Bonus bonus : bonuses) {
			if (bonus.getEventClass() == eventClass) {
				appliedBonuses.add(bonus);
			}
		}
		return appliedBonuses;
	}

	Connection getPlayer();
}
