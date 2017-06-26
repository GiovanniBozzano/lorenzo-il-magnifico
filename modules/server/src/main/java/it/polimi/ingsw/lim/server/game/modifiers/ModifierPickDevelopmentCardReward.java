package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.game.events.EventPickDevelopmentCard;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>You don’t take the bonuses when you take a {@code cardType} from the
 * {@code rows} floors of the towers (through a Family Member or as an effect of
 * another card).
 */
public class ModifierPickDevelopmentCardReward extends Modifier<EventPickDevelopmentCard>
{
	private final CardType cardType;
	private final List<Row> rows;

	public ModifierPickDevelopmentCardReward(String description, CardType cardType, List<Row> rows)
	{
		super(EventPickDevelopmentCard.class, description);
		this.cardType = cardType;
		this.rows = new ArrayList<>(rows);
	}

	@Override
	public void apply(EventPickDevelopmentCard event)
	{
		if (event.getCardType() == this.cardType && this.rows.contains(event.getRow())) {
			event.setGetBoardPositionReward(false);
		}
	}
}
