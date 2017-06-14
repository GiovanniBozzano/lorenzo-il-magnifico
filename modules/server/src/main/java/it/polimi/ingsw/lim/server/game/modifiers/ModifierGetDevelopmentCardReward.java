package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;

/**
 * <p>You don’t take the bonuses when you take a {@code cardType} from the
 * {@code rows} floors of the towers (through a Family Member or as an
 * effect of another card).
 */
public class ModifierGetDevelopmentCardReward extends Modifier<EventGetDevelopmentCard>
{
	private final CardType cardType;
	private final Row[] rows;

	public ModifierGetDevelopmentCardReward(String description, CardType cardType, Row[] rows)
	{
		super(EventGetDevelopmentCard.class, description);
		this.cardType = cardType;
		this.rows = rows;
	}

	@Override
	public void apply(EventGetDevelopmentCard event)
	{
	}
}
