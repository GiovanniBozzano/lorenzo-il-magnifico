package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.events.EventGetCard;
import it.polimi.ingsw.lim.common.game.ResourceAmount;

public class BonusPicoDellaMirandola extends Bonus<EventGetCard>
{
	private static BonusPicoDellaMirandola INSTANCE = new BonusPicoDellaMirandola();

	/**
	 * <p>REQUIREMENT: 4 Venture Cards and 2 Building Cards
	 * <p>PERMANENT ABILITY:
	 * When you take Development Cards, you get a discount of 3 coins (if the
	 * card you are taking has coins in its cost.) This is not a discount on the
	 * coins you must spend if you take a Development Card from a Tower that’s
	 * already occupied.
	 */
	private BonusPicoDellaMirandola()
	{
		super(EventGetCard.class);
	}

	@Override
	public EventGetCard apply(EventGetCard event)
	{
		for (ResourceAmount resourceAmount : event.getCost()) {
			if (resourceAmount.getResourceType() == ResourceType.COIN) {
				resourceAmount.setAmount(resourceAmount.getAmount() - 3);
				return event;
			}
		}
		return event;
	}

	public BonusPicoDellaMirandola getInstance()
	{
		return BonusPicoDellaMirandola.INSTANCE;
	}
}
