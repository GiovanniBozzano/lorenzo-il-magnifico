package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.server.game.cards.CardsHandler;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PlayerCardHandler
{
	private final Map<CardType, List<DevelopmentCard>> developmentCards = new EnumMap<>(CardType.class);
	private final List<LeaderCard> cardsLeader = new ArrayList<>();

	PlayerCardHandler()
	{
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
		this.developmentCards.get(developmentCard.getCardType()).add(CardsHandler.getDevelopmentCardsTypes().get(developmentCard.getCardType()).cast(developmentCard));
	}

	public void addCardLeader(LeaderCard leaderCard)
	{
		this.cardsLeader.add(leaderCard);
	}

	public boolean canAddDevelopmentCard(CardType cardType)
	{
		return this.developmentCards.get(cardType).size() < 6;
	}

	public boolean canAddCardLeader()
	{
		return this.cardsLeader.size() < 4;
	}

	public List<LeaderCard> getCardsLeader()
	{
		return this.cardsLeader;
	}
}
