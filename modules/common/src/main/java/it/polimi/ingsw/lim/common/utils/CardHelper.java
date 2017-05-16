package it.polimi.ingsw.lim.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.CardBlue;
import it.polimi.ingsw.lim.common.enums.CardGreen;
import it.polimi.ingsw.lim.common.enums.CardPurple;
import it.polimi.ingsw.lim.common.enums.CardYellow;
import it.polimi.ingsw.lim.common.json.CardsBlueDeserializer;
import it.polimi.ingsw.lim.common.json.CardsGreenDeserializer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.logging.Level;

public class CardHelper
{
	private static CardsDeck<CardGreen> cardsGreen;
	private static CardsDeck<CardBlue> cardsBlue;
	private final CardBlue[] blueOrder = new CardBlue[4];
	private final CardGreen[] greenOrder = new CardGreen[4];
	private final CardPurple[] purpleOrder = new CardPurple[4];
	private final CardYellow[] yellowOrder = new CardYellow[4];

	public static void initializeCards()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		Type cardsDeckGreenType = new TypeToken<CardsDeck<CardGreen>>()
		{
		}.getType();
		Type cardsDeckBlueType = new TypeToken<CardsDeck<CardBlue>>()
		{
		}.getType();
		gsonBuilder.registerTypeAdapter(cardsDeckGreenType, new CardsGreenDeserializer());
		gsonBuilder.registerTypeAdapter(cardsDeckBlueType, new CardsBlueDeserializer());
		Gson gson = gsonBuilder.create();
		try (Reader reader = new InputStreamReader(CardHelper.class.getResourceAsStream("/json/development_cards_green.json"), "UTF-8")) {
			CardHelper.cardsGreen = gson.fromJson(reader, cardsDeckGreenType);
		} catch (IOException exception) {
			Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		Instance.getLogger().log(Level.INFO, "***** GREEN CARDS *****");
		Instance.getLogger().log(Level.INFO, "+++++ FIRST PERIOD +++++");
		for (CardGreen cardGreen : CardHelper.cardsGreen.getFirstPeriod()) {
			Instance.getLogger().log(Level.INFO, cardGreen.getDisplayName());
		}
		Instance.getLogger().log(Level.INFO, "+++++ SECOND PERIOD +++++");
		for (CardGreen cardGreen : CardHelper.cardsGreen.getSecondPeriod()) {
			Instance.getLogger().log(Level.INFO, cardGreen.getDisplayName());
		}
		Instance.getLogger().log(Level.INFO, "+++++ THIRD PERIOD +++++");
		for (CardGreen cardGreen : CardHelper.cardsGreen.getThirdPeriod()) {
			Instance.getLogger().log(Level.INFO, cardGreen.getDisplayName());
		}
		try (Reader reader = new InputStreamReader(CardHelper.class.getResourceAsStream("/json/development_cards_blue.json"), "UTF-8")) {
			CardHelper.cardsBlue = gson.fromJson(reader, cardsDeckBlueType);
		} catch (IOException exception) {
			Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		Instance.getLogger().log(Level.INFO, "***** BLUE CARDS *****");
		Instance.getLogger().log(Level.INFO, "+++++ FIRST PERIOD +++++");
		for (CardBlue cardBlue : CardHelper.cardsBlue.getFirstPeriod()) {
			Instance.getLogger().log(Level.INFO, cardBlue.getDisplayName());
		}
		Instance.getLogger().log(Level.INFO, "+++++ SECOND PERIOD +++++");
		for (CardBlue cardBlue : CardHelper.cardsBlue.getSecondPeriod()) {
			Instance.getLogger().log(Level.INFO, cardBlue.getDisplayName());
		}
		Instance.getLogger().log(Level.INFO, "+++++ THIRD PERIOD +++++");
		for (CardBlue cardBlue : CardHelper.cardsBlue.getThirdPeriod()) {
			Instance.getLogger().log(Level.INFO, cardBlue.getDisplayName());
		}
	}

	public CardBlue[] getBlueOrder()
	{
		return this.blueOrder;
	}

	public CardGreen[] getGreenOrder()
	{
		return this.greenOrder;
	}

	public CardPurple[] getPurpleOrder()
	{
		return this.purpleOrder;
	}

	public CardYellow[] getYellowOrder()
	{
		return this.yellowOrder;
	}
}
