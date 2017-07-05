package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.CardAmount;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;

import java.util.*;

/**
 * <p>This class handles a player's cards information. It is used to store game
 * cards data concerning only this player.
 */
public class PlayerCardHandler
{
	private static final Map<Integer, Integer> DEVELOPMENT_CARDS_CHARACTER_PRICES = new HashMap<>();
	private static final Map<Integer, Integer> DEVELOPMENT_CARDS_TERRITORY_PRICES = new HashMap<>();

	static {
		PlayerCardHandler.DEVELOPMENT_CARDS_CHARACTER_PRICES.put(0, 0);
		PlayerCardHandler.DEVELOPMENT_CARDS_CHARACTER_PRICES.put(1, 1);
		PlayerCardHandler.DEVELOPMENT_CARDS_CHARACTER_PRICES.put(2, 3);
		PlayerCardHandler.DEVELOPMENT_CARDS_CHARACTER_PRICES.put(3, 6);
		PlayerCardHandler.DEVELOPMENT_CARDS_CHARACTER_PRICES.put(4, 10);
		PlayerCardHandler.DEVELOPMENT_CARDS_CHARACTER_PRICES.put(5, 15);
		PlayerCardHandler.DEVELOPMENT_CARDS_CHARACTER_PRICES.put(6, 21);
	}

	static {
		PlayerCardHandler.DEVELOPMENT_CARDS_TERRITORY_PRICES.put(0, 0);
		PlayerCardHandler.DEVELOPMENT_CARDS_TERRITORY_PRICES.put(1, 0);
		PlayerCardHandler.DEVELOPMENT_CARDS_TERRITORY_PRICES.put(2, 0);
		PlayerCardHandler.DEVELOPMENT_CARDS_TERRITORY_PRICES.put(3, 1);
		PlayerCardHandler.DEVELOPMENT_CARDS_TERRITORY_PRICES.put(4, 4);
		PlayerCardHandler.DEVELOPMENT_CARDS_TERRITORY_PRICES.put(5, 10);
		PlayerCardHandler.DEVELOPMENT_CARDS_TERRITORY_PRICES.put(6, 20);
	}

	private final Map<CardType, List<DevelopmentCard>> developmentCards = new EnumMap<>(CardType.class);
	private final List<LeaderCard> leaderCards = new ArrayList<>();

	PlayerCardHandler()
	{
		this.developmentCards.put(CardType.BUILDING, new ArrayList<>());
		this.developmentCards.put(CardType.CHARACTER, new ArrayList<>());
		this.developmentCards.put(CardType.TERRITORY, new ArrayList<>());
		this.developmentCards.put(CardType.VENTURE, new ArrayList<>());
	}

	public boolean hasEnoughCards(List<CardAmount> cardAmounts)
	{
		for (CardAmount cardAmount : cardAmounts) {
			if (this.developmentCards.get(cardAmount.getCardType()).size() < cardAmount.getAmount()) {
				return false;
			}
		}
		return true;
	}

	public <T extends DevelopmentCard> List<T> getDevelopmentCards(CardType cardType, Class<T> cardClass)
	{
		List<T> cardList = new ArrayList<>();
		for (DevelopmentCard developmentCard : this.developmentCards.get(cardType)) {
			cardList.add(cardClass.cast(developmentCard));
		}
		return cardList;
	}

	public <T> T getDevelopmentCardFromIndex(CardType cardType, int index, Class<T> cardClass) throws NoSuchElementException
	{
		for (DevelopmentCard developmentCard : this.developmentCards.get(cardType)) {
			if (developmentCard.getIndex() == index) {
				return cardClass.cast(developmentCard);
			}
		}
		throw new NoSuchElementException();
	}

	int getDevelopmentCardsNumber(CardType cardType)
	{
		return this.developmentCards.get(cardType).size();
	}

	public void addDevelopmentCard(DevelopmentCard developmentCard)
	{
		this.developmentCards.get(developmentCard.getCardType()).add(developmentCard);
	}

	public boolean canAddDevelopmentCard(CardType cardType)
	{
		return this.developmentCards.get(cardType).size() < 6;
	}

	public void addLeaderCard(LeaderCard leaderCard)
	{
		this.leaderCards.add(leaderCard);
	}

	static Map<Integer, Integer> getDevelopmentCardsCharacterPrices()
	{
		return PlayerCardHandler.DEVELOPMENT_CARDS_CHARACTER_PRICES;
	}

	static Map<Integer, Integer> getDevelopmentCardsTerritoryPrices()
	{
		return PlayerCardHandler.DEVELOPMENT_CARDS_TERRITORY_PRICES;
	}

	public List<LeaderCard> getLeaderCards()
	{
		return this.leaderCards;
	}
}
