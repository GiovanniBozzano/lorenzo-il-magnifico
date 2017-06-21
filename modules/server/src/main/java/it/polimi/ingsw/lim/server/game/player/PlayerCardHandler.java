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
	private final List<LeaderCard> leaderCards = new ArrayList<>();

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

	public int getDevelopmentCardsNumber(CardType cardType)
	{
		return this.developmentCards.get(cardType).size();
	}

	public void addDevelopmentCard(DevelopmentCard developmentCard)
	{
		this.developmentCards.get(developmentCard.getCardType()).add(CardsHandler.getDevelopmentCardsTypes().get(developmentCard.getCardType()).cast(developmentCard));
	}

	public boolean canAddDevelopmentCard(CardType cardType)
	{
		return this.developmentCards.get(cardType).size() < 6;
	}

	public void addLeaderCard(LeaderCard leaderCard)
	{
		this.leaderCards.add(leaderCard);
	}

	public LeaderCard getLeaderCardFromIndex(int index)
	{
		for (LeaderCard leaderCard : this.leaderCards) {
			if (leaderCard.getIndex() == index) {
				return leaderCard;
			}
		}
		return null;
	}

	public List<LeaderCard> getLeaderCards()
	{
		return this.leaderCards;
	}
}
