package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.server.game.cards.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCardHandler
{
	private final PlayerInformations playerInformations;
	private final Map<CardType, List> developmentCardsLists = new HashMap<>();
	private final List<DevelopmentCardBuilding> developmentCardsBuilding = new ArrayList<>();
	private final List<DevelopmentCardCharacter> developmentCardsCharacter = new ArrayList<>();
	private final List<DevelopmentCardTerritory> developmentCardsTerritory = new ArrayList<>();
	private final List<DevelopmentCardVenture> developmentCardsVenture = new ArrayList<>();
	private final List<CardLeader> cardsLeader = new ArrayList<>();

	PlayerCardHandler(PlayerInformations playerInformations)
	{
		this.playerInformations = playerInformations;
		this.developmentCardsLists.put(CardType.BUILDING, this.developmentCardsBuilding);
		this.developmentCardsLists.put(CardType.CHARACTER, this.developmentCardsCharacter);
		this.developmentCardsLists.put(CardType.TERRITORY, this.developmentCardsTerritory);
		this.developmentCardsLists.put(CardType.VENTURE, this.developmentCardsVenture);
	}

	@SuppressWarnings("unchecked")
	public void addDevelopmentCard(DevelopmentCard developmentCard)
	{
		this.developmentCardsLists.get(developmentCard.getCardType()).add(CardsHandler.DEVELOPMENT_CARDS_TYPES.get(developmentCard.getCardType()).cast(developmentCard));
	}

	public void addDevelopmentCard(DevelopmentCardBuilding developmentCardBuilding)
	{
		this.developmentCardsBuilding.add(developmentCardBuilding);
	}

	public void addDevelopmentCard(DevelopmentCardCharacter developmentCardCharacter)
	{
		this.developmentCardsCharacter.add(developmentCardCharacter);
	}

	public void addDevelopmentCard(DevelopmentCardTerritory developmentCardTerritory)
	{
		this.developmentCardsTerritory.add(developmentCardTerritory);
	}

	public void addDevelopmentCard(DevelopmentCardVenture developmentCardVenture)
	{
		this.developmentCardsVenture.add(developmentCardVenture);
	}

	public void addCardLeader(CardLeader cardLeader)
	{
		this.cardsLeader.add(cardLeader);
	}

	public boolean canAddDevelopmentCard(CardType cardType)
	{
		return this.developmentCardsLists.get(cardType).size() < 6 && (cardType != CardType.TERRITORY || this.playerInformations.getPlayerResourceHandler().isTerritorySlotAvailable(this.developmentCardsTerritory.size()));
	}

	public boolean canAddCardLeader()
	{
		return this.cardsLeader.size() < 4;
	}

	public List<CardLeader> getCardsLeader()
	{
		return this.cardsLeader;
	}
}
