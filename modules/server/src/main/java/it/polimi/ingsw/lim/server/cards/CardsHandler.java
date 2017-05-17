package it.polimi.ingsw.lim.server.cards;

import it.polimi.ingsw.lim.common.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardCharacter;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardVenture;

public class CardsHandler
{
	public static final DevelopmentCardsDeck<DevelopmentCardTerritory> CARDS_TERRITORY = new DevelopmentCardsDeck.Builder<>(DevelopmentCardTerritory.class, "/json/development_cards_territory.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardCharacter> CARDS_CHARACTER = new DevelopmentCardsDeck.Builder<>(DevelopmentCardCharacter.class, "/json/development_cards_character.json").initialize();
	private final DevelopmentCardCharacter[] currentDevelopmentCardsCharacters = new DevelopmentCardCharacter[4];
	private final DevelopmentCardTerritory[] currentDevelopmentCardsTerritory = new DevelopmentCardTerritory[4];
	private final DevelopmentCardVenture[] currentDevelopmentCardsVenture = new DevelopmentCardVenture[4];
	private final DevelopmentCardBuilding[] currentDevelopmentCardsBuilding = new DevelopmentCardBuilding[4];

	public DevelopmentCardCharacter[] getCurrentDevelopmentCardsCharacters()
	{
		return this.currentDevelopmentCardsCharacters;
	}

	public DevelopmentCardTerritory[] getCurrentDevelopmentCardsTerritory()
	{
		return this.currentDevelopmentCardsTerritory;
	}

	public DevelopmentCardVenture[] getCurrentDevelopmentCardsVenture()
	{
		return this.currentDevelopmentCardsVenture;
	}

	public DevelopmentCardBuilding[] getCurrentDevelopmentCardsBuilding()
	{
		return this.currentDevelopmentCardsBuilding;
	}
}
