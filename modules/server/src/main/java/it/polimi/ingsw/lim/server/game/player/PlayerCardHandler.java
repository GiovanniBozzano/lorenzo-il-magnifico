package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.server.game.cards.CardLeader;
import it.polimi.ingsw.lim.server.game.cards.CardsHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCardHandler
{
	private final PlayerInformations playerInformations;
	private final Map<CardType, List<DevelopmentCard>> developmentCards = new HashMap<>();
	private final List<CardLeader> cardsLeader = new ArrayList<>();

	PlayerCardHandler(PlayerInformations playerInformations)
	{
		this.playerInformations = playerInformations;
		this.developmentCards.put(CardType.BUILDING, new ArrayList<>());
		this.developmentCards.put(CardType.CHARACTER, new ArrayList<>());
		this.developmentCards.put(CardType.TERRITORY, new ArrayList<>());
		this.developmentCards.put(CardType.VENTURE, new ArrayList<>());
	}

	public <T extends DevelopmentCard> List<T> getDevelopmentCards(CardType cardType, Class<T> cardClass)
	{
		List<T> cardList = new ArrayList<>();
		for (DevelopmentCard developmentCard : this.developmentCards.get(cardType)) {
			cardList.add(cardClass.cast(developmentCard));
		}
		return cardList;
	}

	public <T> T getDevelopmentCardFromIndex(CardType cardType, int index, Class<T> cardClass)
	{
		for (DevelopmentCard developmentCard : this.developmentCards.get(cardType)) {
			if (developmentCard.getIndex() == index) {
				return cardClass.cast(developmentCard);
			}
		}
		return null;
	}

	public void addDevelopmentCard(DevelopmentCard developmentCard)
	{
		this.developmentCards.get(developmentCard.getCardType()).add(CardsHandler.DEVELOPMENT_CARDS_TYPES.get(developmentCard.getCardType()).cast(developmentCard));
	}

	public void addCardLeader(CardLeader cardLeader)
	{
		this.cardsLeader.add(cardLeader);
	}

	public boolean canAddDevelopmentCard(CardType cardType)
	{
		return this.developmentCards.get(cardType).size() < 6 && (cardType != CardType.TERRITORY || this.playerInformations.getPlayerResourceHandler().isTerritorySlotAvailable(this.developmentCards.get(CardType.TERRITORY).size()));
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
