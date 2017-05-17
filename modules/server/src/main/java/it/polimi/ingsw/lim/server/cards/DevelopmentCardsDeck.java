package it.polimi.ingsw.lim.server.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.bonus.BonusAdditionCard;
import it.polimi.ingsw.lim.common.bonus.BonusAdditionWork;
import it.polimi.ingsw.lim.common.bonus.Malus;
import it.polimi.ingsw.lim.common.cards.DevelopmentCard;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardCharacter;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.events.Event;
import it.polimi.ingsw.lim.common.events.EventCard;
import it.polimi.ingsw.lim.common.events.EventWork;
import it.polimi.ingsw.lim.common.utils.DiscountChoice;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.cards.json.DevelopmentCardsCharacterDeserializer;
import it.polimi.ingsw.lim.server.cards.json.DevelopmentCardsTerritoryDeserializer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class DevelopmentCardsDeck<T extends DevelopmentCard>
{
	private final T[] firstPeriod;
	private final T[] secondPeriod;
	private final T[] thirdPeriod;

	public DevelopmentCardsDeck(T[] firstPeriod, T[] secondPeriod, T[] thirdPeriod)
	{
		this.firstPeriod = firstPeriod;
		this.secondPeriod = secondPeriod;
		this.thirdPeriod = thirdPeriod;
	}

	public T[] getFirstPeriod()
	{
		return this.firstPeriod;
	}

	public T[] getSecondPeriod()
	{
		return this.secondPeriod;
	}

	public T[] getThirdPeriod()
	{
		return this.thirdPeriod;
	}

	@Override
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		this.appendPeriod(stringBuilder, this.firstPeriod);
		this.appendPeriod(stringBuilder, this.secondPeriod);
		this.appendPeriod(stringBuilder, this.thirdPeriod);
		return stringBuilder.toString();
	}

	private void appendPeriod(StringBuilder stringBuilder, T[] period)
	{
		for (int indexCards = 0; indexCards < period.length; indexCards++) {
			if (indexCards != 0) {
				stringBuilder.append("****************************************\n");
			} else {
				stringBuilder.append("########################################\n");
				stringBuilder.append("########################################\n");
			}
			stringBuilder.append("display_name: ").append(period[indexCards].getDisplayName()).append("\n");
			stringBuilder.append("period: ").append(period[indexCards].getPeriod().name()).append("\n");
			if (period[indexCards] instanceof DevelopmentCardCharacter) {
				DevelopmentCardCharacter developmentCardCharacter = (DevelopmentCardCharacter) period[indexCards];
				stringBuilder.append("price: ").append(developmentCardCharacter.getPrice()).append("\n");
				stringBuilder.append("instant_reward:\n");
				stringBuilder.append("  events:\n");
				Event[] events = developmentCardCharacter.getInstantReward().getEvents();
				for (int indexEvents = 0; indexEvents < events.length; indexEvents++) {
					if (indexEvents != 0) {
						stringBuilder.append("      ----------\n");
					}
					if (events[indexEvents] instanceof EventCard) {
						EventCard eventCard = (EventCard) events[indexEvents];
						stringBuilder.append("      type: CARD\n");
						stringBuilder.append("      value: ").append(eventCard.getValue()).append("\n");
						stringBuilder.append("      card_types:\n");
						for (CardType cardType : eventCard.getCardTypes()) {
							stringBuilder.append("          ").append(cardType.name()).append("\n");
						}
						stringBuilder.append("      discount_choices:\n");
						DiscountChoice[] discountChoices = eventCard.getDiscountChoices();
						for (int indexDiscountChoices = 0; indexDiscountChoices < discountChoices.length; indexDiscountChoices++) {
							if (indexDiscountChoices != 0) {
								stringBuilder.append("          ----------\n");
							}
							stringBuilder.append("          resource_amounts:\n");
							ResourceAmount[] resourceAmounts = discountChoices[indexDiscountChoices].getResourceAmounts();
							for (int indexResourceAmount = 0; indexResourceAmount < resourceAmounts.length; indexResourceAmount++) {
								if (indexResourceAmount != 0) {
									stringBuilder.append("              ----------\n");
								}
								stringBuilder.append("              type: ").append(resourceAmounts[indexResourceAmount].getResourceType().name()).append("\n");
								stringBuilder.append("              amount: ").append(resourceAmounts[indexResourceAmount].getAmount()).append("\n");
							}
						}
					} else {
						EventWork eventCard = (EventWork) events[indexEvents];
						stringBuilder.append("      type: WORK\n");
						stringBuilder.append("      value: ").append(eventCard.getValue()).append("\n");
						stringBuilder.append("      work_type: ").append(eventCard.getWorkType().name()).append("\n");
					}
				}
				stringBuilder.append("  resource_amounts:\n");
				ResourceAmount[] resourceAmounts = developmentCardCharacter.getInstantReward().getResourceAmounts();
				for (int indexResourceAmounts = 0; indexResourceAmounts < resourceAmounts.length; indexResourceAmounts++) {
					if (indexResourceAmounts != 0) {
						stringBuilder.append("      ----------\n");
					}
					stringBuilder.append("      type: ").append(resourceAmounts[indexResourceAmounts].getResourceType().name()).append("\n");
					stringBuilder.append("      amount: ").append(resourceAmounts[indexResourceAmounts].getAmount()).append("\n");
				}
				stringBuilder.append("permanent_bonus:\n");
				if (developmentCardCharacter.getPermanentBonus() instanceof BonusAdditionCard) {
					BonusAdditionCard bonusAdditionCard = (BonusAdditionCard) developmentCardCharacter.getPermanentBonus();
					stringBuilder.append("  type: ADDITION_CARD\n");
					stringBuilder.append("  value: ").append(bonusAdditionCard.getValue()).append("\n");
					stringBuilder.append("  card_type: ").append(bonusAdditionCard.getCardType().name()).append("\n");
					stringBuilder.append("  discount_choices:\n");
					DiscountChoice[] discountChoices = bonusAdditionCard.getDiscountChoices();
					for (int indexDiscountChoices = 0; indexDiscountChoices < discountChoices.length; indexDiscountChoices++) {
						if (indexDiscountChoices != 0) {
							stringBuilder.append("      ----------\n");
						}
						stringBuilder.append("      resource_amounts:\n");
						resourceAmounts = discountChoices[indexDiscountChoices].getResourceAmounts();
						for (int indexResourceAmounts = 0; indexResourceAmounts < resourceAmounts.length; indexResourceAmounts++) {
							if (indexResourceAmounts != 0) {
								stringBuilder.append("          ----------\n");
							}
							stringBuilder.append("          type: ").append(resourceAmounts[indexResourceAmounts].getResourceType().name()).append("\n");
							stringBuilder.append("          amount: ").append(resourceAmounts[indexResourceAmounts].getAmount()).append("\n");
						}
					}
				} else if (developmentCardCharacter.getPermanentBonus() instanceof BonusAdditionWork) {
					BonusAdditionWork bonusAdditionWork = (BonusAdditionWork) developmentCardCharacter.getPermanentBonus();
					stringBuilder.append("  type: ADDITION_WORK\n");
					stringBuilder.append("  value: ").append(bonusAdditionWork.getValue()).append("\n");
					stringBuilder.append("  work_type: ").append(bonusAdditionWork.getWorkType().name()).append("\n");
				} else if (developmentCardCharacter.getPermanentBonus() instanceof Malus) {
					Malus malus = (Malus) developmentCardCharacter.getPermanentBonus();
					stringBuilder.append("  type: MALUS\n");
					stringBuilder.append("  rows:\n");
					Row[] rows = malus.getRows();
					for (Row row : rows) {
						stringBuilder.append("      ").append(row.name()).append("\n");
					}
				}
			} else if (period[indexCards] instanceof DevelopmentCardTerritory) {
				DevelopmentCardTerritory developmentCardTerritory = (DevelopmentCardTerritory) period[indexCards];
				stringBuilder.append("instant_resources:\n");
				ResourceAmount[] resourceAmounts = developmentCardTerritory.getInstantResources();
				for (int indexResourceAmounts = 0; indexResourceAmounts < resourceAmounts.length; indexResourceAmounts++) {
					if (indexResourceAmounts != 0) {
						stringBuilder.append("      ----------\n");
					}
					stringBuilder.append("      type: ").append(resourceAmounts[indexResourceAmounts].getResourceType().name()).append("\n");
					stringBuilder.append("      amount: ").append(resourceAmounts[indexResourceAmounts].getAmount()).append("\n");
				}
				stringBuilder.append("activation_cost: ").append(developmentCardTerritory.getActivationCost()).append("\n");
				stringBuilder.append("harvest_resources:\n");
				resourceAmounts = developmentCardTerritory.getHarvestResources();
				for (int indexResourceAmounts = 0; indexResourceAmounts < resourceAmounts.length; indexResourceAmounts++) {
					if (indexResourceAmounts != 0) {
						stringBuilder.append("      ----------\n");
					}
					stringBuilder.append("      type: ").append(resourceAmounts[indexResourceAmounts].getResourceType().name()).append("\n");
					stringBuilder.append("      amount: ").append(resourceAmounts[indexResourceAmounts].getAmount()).append("\n");
				}
			}
		}
	}

	static class Builder<T extends DevelopmentCard>
	{
		private static final Map<Class<? extends DevelopmentCard>, Type> TYPE_TOKENS = new HashMap<>();

		static {
			Builder.TYPE_TOKENS.put(DevelopmentCardTerritory.class, new TypeToken<DevelopmentCardsDeck<DevelopmentCardTerritory>>()
			{
			}.getType());
			Builder.TYPE_TOKENS.put(DevelopmentCardCharacter.class, new TypeToken<DevelopmentCardsDeck<DevelopmentCardCharacter>>()
			{
			}.getType());
		}

		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapter(Builder.TYPE_TOKENS.get(DevelopmentCardTerritory.class), new DevelopmentCardsTerritoryDeserializer()).registerTypeAdapter(Builder.TYPE_TOKENS.get(DevelopmentCardCharacter.class), new DevelopmentCardsCharacterDeserializer());
		private static final Gson GSON = Builder.GSON_BUILDER.create();
		private final Class<T> clazz;
		private final String jsonFile;

		Builder(Class<T> clazz, String jsonFile)
		{
			this.clazz = clazz;
			this.jsonFile = jsonFile;
		}

		DevelopmentCardsDeck<T> initialize()
		{
			try (Reader reader = new InputStreamReader(Builder.class.getResourceAsStream(this.jsonFile), "UTF-8")) {
				return Builder.GSON.fromJson(reader, Builder.TYPE_TOKENS.get(this.clazz));
			} catch (IOException exception) {
				Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			return null;
		}
	}
}
