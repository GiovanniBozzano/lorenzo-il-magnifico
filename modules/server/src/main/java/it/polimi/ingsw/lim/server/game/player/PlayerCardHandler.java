package it.polimi.ingsw.lim.server.game.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.DevelopmentCardAmount;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;

/**
 * <p>This class handles a player's cards information. It is used to store game
 * cards data concerning only this player.
 */
public class PlayerCardHandler
{
	private static final Map<Integer, Integer> DEVELOPMENT_CARDS_CHARACTER_REWARDS = new DevelopmentCardsVictoryPointsBuilder("/json/development_cards_character_rewards.json").initialize();
	private static final Map<Integer, Integer> DEVELOPMENT_CARDS_TERRITORY_REWARDS = new DevelopmentCardsVictoryPointsBuilder("/json/development_cards_territory_rewards.json").initialize();
	private final Map<CardType, List<DevelopmentCard>> developmentCards = new EnumMap<>(CardType.class);
	private final List<LeaderCard> leaderCards = new ArrayList<>();

	PlayerCardHandler()
	{
		this.developmentCards.put(CardType.BUILDING, new ArrayList<>());
		this.developmentCards.put(CardType.CHARACTER, new ArrayList<>());
		this.developmentCards.put(CardType.TERRITORY, new ArrayList<>());
		this.developmentCards.put(CardType.VENTURE, new ArrayList<>());
	}

	public boolean hasEnoughCards(List<DevelopmentCardAmount> developmentCardAmounts)
	{
		for (DevelopmentCardAmount developmentCardAmount : developmentCardAmounts) {
			if (this.developmentCards.get(developmentCardAmount.getCardType()).size() < developmentCardAmount.getAmount()) {
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

	public <T> T getDevelopmentCardFromIndex(CardType cardType, int index, Class<T> cardClass)
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

	static Map<Integer, Integer> getDevelopmentCardsCharacterRewards()
	{
		return PlayerCardHandler.DEVELOPMENT_CARDS_CHARACTER_REWARDS;
	}

	static Map<Integer, Integer> getDevelopmentCardsTerritoryRewards()
	{
		return PlayerCardHandler.DEVELOPMENT_CARDS_TERRITORY_REWARDS;
	}

	public List<LeaderCard> getLeaderCards()
	{
		return this.leaderCards;
	}

	private static class DevelopmentCardsVictoryPointsBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = DevelopmentCardsVictoryPointsBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		DevelopmentCardsVictoryPointsBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Integer, Integer> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return DevelopmentCardsVictoryPointsBuilder.GSON.fromJson(reader, new TypeToken<Map<Integer, Integer>>()
				{
				}.getType());
			} catch (IOException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new HashMap<>();
		}
	}
}
