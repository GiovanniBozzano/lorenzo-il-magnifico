package it.polimi.ingsw.lim.server.cards;

import it.polimi.ingsw.lim.common.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardCharacter;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardVenture;

public class CardsHandler
{
	public static final CardsDeck<DevelopmentCardTerritory> CARDS_GREEN = new CardsDeck.Builder<>(DevelopmentCardTerritory.class, "/json/development_cards_green.json").initialize();
	public static final CardsDeck<DevelopmentCardCharacter> CARDS_BLUE = new CardsDeck.Builder<>(DevelopmentCardCharacter.class, "/json/development_cards_blue.json").initialize();
	private final DevelopmentCardCharacter[] blueOrder = new DevelopmentCardCharacter[4];
	private final DevelopmentCardTerritory[] greenOrder = new DevelopmentCardTerritory[4];
	private final DevelopmentCardVenture[] purpleOrder = new DevelopmentCardVenture[4];
	private final DevelopmentCardBuilding[] yellowOrder = new DevelopmentCardBuilding[4];

	public DevelopmentCardCharacter[] getBlueOrder()
	{
		return this.blueOrder;
	}

	public DevelopmentCardTerritory[] getGreenOrder()
	{
		return this.greenOrder;
	}

	public DevelopmentCardVenture[] getPurpleOrder()
	{
		return this.purpleOrder;
	}

	public DevelopmentCardBuilding[] getYellowOrder()
	{
		return this.yellowOrder;
	}
}
