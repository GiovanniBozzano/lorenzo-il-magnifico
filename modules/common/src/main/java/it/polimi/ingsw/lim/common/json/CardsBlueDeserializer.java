package it.polimi.ingsw.lim.common.json;

import com.google.gson.*;
import it.polimi.ingsw.lim.common.bonus.BonusAdditionCard;
import it.polimi.ingsw.lim.common.bonus.BonusAdditionWork;
import it.polimi.ingsw.lim.common.bonus.Malus;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.events.Event;
import it.polimi.ingsw.lim.common.events.EventCard;
import it.polimi.ingsw.lim.common.events.EventWork;
import it.polimi.ingsw.lim.common.utils.CardsDeck;
import it.polimi.ingsw.lim.common.utils.DiscountChoice;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.Reward;

import java.lang.reflect.Type;

public class CardsBlueDeserializer implements JsonDeserializer<CardsDeck<CardBlue>>
{
	@Override
	public CardsDeck<CardBlue> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
	{
		CardBlue[] periodFirst = CardsBlueDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_first").getAsJsonArray());
		CardBlue[] periodSecond = CardsBlueDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_second").getAsJsonArray());
		CardBlue[] periodThird = CardsBlueDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_third").getAsJsonArray());
		return new CardsDeck<>(periodFirst, periodSecond, periodThird);
	}

	private static CardBlue[] getPeriodCards(JsonArray period)
	{
		CardBlue[] cards = new CardBlue[8];
		for (int index = 0; index < period.size() && index < 8; index++) {
			JsonObject card = period.get(index).getAsJsonObject();
			String displayName = card.get("display_name").getAsString();
			int price = card.get("price").getAsInt();
			JsonObject instantReward = card.get("instant_reward").getAsJsonObject();
			JsonArray instantRewardEventsJson = instantReward.get("events").getAsJsonArray();
			Event[] instantRewardEvents = new Event[instantRewardEventsJson.size()];
			for (int indexInstantRewardEvents = 0; indexInstantRewardEvents < instantRewardEventsJson.size(); indexInstantRewardEvents++) {
				JsonObject instantRewardEvent = instantRewardEventsJson.get(indexInstantRewardEvents).getAsJsonObject();
				int value = instantRewardEvent.get("value").getAsInt();
				if (EventType.valueOf(instantRewardEvent.get("type").getAsString()) == EventType.CARD) {
					JsonArray cardTypesJson = instantRewardEvent.get("card_types").getAsJsonArray();
					CardType[] cardTypes = new CardType[cardTypesJson.size()];
					for (int indexCardTypes = 0; indexCardTypes < cardTypesJson.size(); indexCardTypes++) {
						cardTypes[indexCardTypes] = CardType.valueOf(cardTypesJson.get(indexCardTypes).getAsString());
					}
					JsonArray discountChoicesJson = instantRewardEvent.get("discount_choices").getAsJsonArray();
					DiscountChoice[] discountChoices = new DiscountChoice[discountChoicesJson.size()];
					for (int indexDiscountChoices = 0; indexDiscountChoices < discountChoicesJson.size(); indexDiscountChoices++) {
						JsonArray resourceAmountsJson = discountChoicesJson.get(indexDiscountChoices).getAsJsonArray();
						ResourceAmount[] resourceAmounts = new ResourceAmount[resourceAmountsJson.size()];
						for (int indexResourceAmounts = 0; indexResourceAmounts < resourceAmountsJson.size(); indexResourceAmounts++) {
							JsonObject resourceAmount = resourceAmountsJson.get(indexResourceAmounts).getAsJsonObject();
							resourceAmounts[indexResourceAmounts] = new ResourceAmount(ResourceType.valueOf(resourceAmount.get("type").getAsString()), resourceAmount.get("amount").getAsInt());
						}
						discountChoices[indexDiscountChoices] = new DiscountChoice(resourceAmounts);
					}
					instantRewardEvents[indexInstantRewardEvents] = new EventCard(value, cardTypes, discountChoices);
				} else {
					WorkType workType = WorkType.valueOf(instantRewardEvent.get("work_type").getAsString());
					instantRewardEvents[indexInstantRewardEvents] = new EventWork(value, workType);
				}
			}
			JsonArray instantRewardResourceAmountsJson = instantReward.get("resource_amounts").getAsJsonArray();
			ResourceAmount[] instantRewardResourceAmounts = new ResourceAmount[instantRewardResourceAmountsJson.size()];
			for (int indexInstantRewardResourceAmounts = 0; indexInstantRewardResourceAmounts < instantRewardResourceAmountsJson.size(); indexInstantRewardResourceAmounts++) {
				JsonObject instantRewardResourceAmount = instantRewardResourceAmountsJson.get(indexInstantRewardResourceAmounts).getAsJsonObject();
				if (instantRewardResourceAmount.has("card_type")) {
					instantRewardResourceAmounts[indexInstantRewardResourceAmounts] = new ResourceAmount(ResourceType.valueOf(instantRewardResourceAmount.get("type").getAsString()), instantRewardResourceAmount.get("amount").getAsInt(), CardType.valueOf(instantRewardResourceAmount.get("card_type").getAsString()));
				} else {
					instantRewardResourceAmounts[indexInstantRewardResourceAmounts] = new ResourceAmount(ResourceType.valueOf(instantRewardResourceAmount.get("type").getAsString()), instantRewardResourceAmount.get("amount").getAsInt());
				}
			}
			if (card.get("permanent_bonus").isJsonNull()) {
				cards[index] = new CardBlue(displayName, Period.FIRST, price, new Reward(instantRewardEvents, instantRewardResourceAmounts), null);
			} else {
				JsonObject permanentBonusJson = card.get("permanent_bonus").getAsJsonObject();
				Object permanentBonus;
				if (BonusType.valueOf(permanentBonusJson.get("type").getAsString()) == BonusType.ADDITION_CARD) {
					int value = permanentBonusJson.get("value").getAsInt();
					CardType cardType = CardType.valueOf(permanentBonusJson.get("card_type").getAsString());
					JsonArray discountChoicesJson = permanentBonusJson.get("discount_choices").getAsJsonArray();
					DiscountChoice[] discountChoices = new DiscountChoice[discountChoicesJson.size()];
					for (int indexDiscountChoices = 0; indexDiscountChoices < discountChoicesJson.size(); indexDiscountChoices++) {
						JsonArray resourceAmountsJson = discountChoicesJson.get(indexDiscountChoices).getAsJsonArray();
						ResourceAmount[] resourceAmounts = new ResourceAmount[resourceAmountsJson.size()];
						for (int indexResourceAmounts = 0; indexResourceAmounts < resourceAmountsJson.size(); indexResourceAmounts++) {
							JsonObject resourceAmount = resourceAmountsJson.get(indexResourceAmounts).getAsJsonObject();
							resourceAmounts[indexResourceAmounts] = new ResourceAmount(ResourceType.valueOf(resourceAmount.get("type").getAsString()), resourceAmount.get("amount").getAsInt());
						}
						discountChoices[indexDiscountChoices] = new DiscountChoice(resourceAmounts);
					}
					permanentBonus = new BonusAdditionCard(value, cardType, discountChoices);
				} else if (BonusType.valueOf(permanentBonusJson.get("type").getAsString()) == BonusType.ADDITION_WORK) {
					int value = permanentBonusJson.get("value").getAsInt();
					WorkType workType = WorkType.valueOf(permanentBonusJson.get("work_type").getAsString());
					permanentBonus = new BonusAdditionWork(value, workType);
				} else {
					JsonArray rowsJson = permanentBonusJson.get("rows").getAsJsonArray();
					Row[] rows = new Row[rowsJson.size()];
					for (int indexRows = 0; indexRows < rowsJson.size(); indexRows++) {
						rows[indexRows] = Row.valueOf(rowsJson.get(indexRows).getAsString());
					}
					permanentBonus = new Malus(rows);
				}
				cards[index] = new CardBlue(displayName, Period.FIRST, price, new Reward(instantRewardEvents, instantRewardResourceAmounts), permanentBonus);
			}
		}
		return cards;
	}
}
