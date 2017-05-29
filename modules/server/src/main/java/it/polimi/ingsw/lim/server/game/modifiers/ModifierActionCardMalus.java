package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.game.events.EventGetCard;

/**
 * <p>You don’t take the bonuses when you take a {@code cardType} from the
 * {@code rows} floors of the towers (through a Family Member or as an
 * effect of another card).
 */
public class ModifierActionCardMalus extends Modifier<EventGetCard>
{
	private final CardType cardType;
	private final Row[] rows;

	public ModifierActionCardMalus(CardType cardType, Row[] rows)
	{
		super(EventGetCard.class);
		this.cardType = cardType;
		this.rows = rows;
	}

	@Override
	public void apply(EventGetCard event)
	{
	}
}
