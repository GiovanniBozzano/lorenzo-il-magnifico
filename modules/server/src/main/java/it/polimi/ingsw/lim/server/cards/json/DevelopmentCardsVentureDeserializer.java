package it.polimi.ingsw.lim.server.cards.json;

import com.google.gson.*;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardVenture;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.events.Event;
import it.polimi.ingsw.lim.common.events.EventCard;
import it.polimi.ingsw.lim.common.events.EventWork;
import it.polimi.ingsw.lim.common.utils.*;
import it.polimi.ingsw.lim.server.cards.DevelopmentCardsDeck;

import java.lang.reflect.Type;

public class DevelopmentCardsVentureDeserializer implements JsonDeserializer<DevelopmentCardsDeck<DevelopmentCardVenture>>
{
	@Override
	public DevelopmentCardsDeck<DevelopmentCardVenture> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
	{
		DevelopmentCardVenture[] periodFirst = DevelopmentCardsVentureDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_first").getAsJsonArray(), Period.FIRST);
		DevelopmentCardVenture[] periodSecond = DevelopmentCardsVentureDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_second").getAsJsonArray(), Period.SECOND);
		DevelopmentCardVenture[] periodThird = DevelopmentCardsVentureDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_third").getAsJsonArray(), Period.THIRD);
		return new DevelopmentCardsDeck<>(periodFirst, periodSecond, periodThird);
	}

	private static DevelopmentCardVenture[] getPeriodCards(JsonArray periodArray, Period period)
	{
		DevelopmentCardVenture[] cards = new DevelopmentCardVenture[8];
		for (int index = 0; index < periodArray.size() && index < 8; index++) {
			JsonObject card = periodArray.get(index).getAsJsonObject();
			String displayName = card.get("display_name").getAsString();
			JsonArray resourceTradeOptionsJson = card.get("resource_trade_options").getAsJsonArray();
			ResourceTradeOption[] resourceTradeOptions = new ResourceTradeOption[resourceTradeOptionsJson.size()];
			for (int indexResourceTradeOptions = 0; indexResourceTradeOptions < resourceTradeOptionsJson.size(); indexResourceTradeOptions++) {
				JsonArray employedResourcesJson = resourceTradeOptionsJson.get(indexResourceTradeOptions).getAsJsonArray();
				ResourceAmount[] employedResources = new ResourceAmount[employedResourcesJson.size()];
				for (int indexEmployedResources = 0; indexEmployedResources < employedResourcesJson.size(); indexEmployedResources++) {
					JsonObject employedResource = employedResourcesJson.get(indexEmployedResources).getAsJsonObject();
					employedResources[indexEmployedResources] = new ResourceAmount(ResourceType.valueOf(employedResource.get("type").getAsString()), employedResource.get("amount").getAsInt());
				}
				JsonArray producedResourcesJson = resourceTradeOptionsJson.get(indexResourceTradeOptions).getAsJsonArray();
				ResourceAmount[] producedResources = new ResourceAmount[producedResourcesJson.size()];
				for (int indexProducedResources = 0; indexProducedResources < producedResourcesJson.size(); indexProducedResources++) {
					JsonObject producedResource = producedResourcesJson.get(indexProducedResources).getAsJsonObject();
					if (producedResource.has("card_type")) {
						producedResources[indexProducedResources] = new ResourceAmountMultiplierCard(ResourceType.valueOf(producedResource.get("type").getAsString()), producedResource.get("amount").getAsInt(), CardType.valueOf(producedResource.get("card_type").getAsString()));
					} else {
						producedResources[indexProducedResources] = new ResourceAmount(ResourceType.valueOf(producedResource.get("type").getAsString()), producedResource.get("amount").getAsInt());
					}
				}
				resourceTradeOptions[indexResourceTradeOptions] = new ResourceTradeOption(employedResources, producedResources);
			}
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
					instantRewardResourceAmounts[indexInstantRewardResourceAmounts] = new ResourceAmountMultiplierCard(ResourceType.valueOf(instantRewardResourceAmount.get("type").getAsString()), instantRewardResourceAmount.get("amount").getAsInt(), CardType.valueOf(instantRewardResourceAmount.get("card_type").getAsString()));
				} else {
					instantRewardResourceAmounts[indexInstantRewardResourceAmounts] = new ResourceAmount(ResourceType.valueOf(instantRewardResourceAmount.get("type").getAsString()), instantRewardResourceAmount.get("amount").getAsInt());
				}
			}
			int victoryValue = card.get("victory_value").getAsInt();
			cards[index] = new DevelopmentCardVenture(displayName, period, resourceTradeOptions, new Reward(instantRewardEvents, instantRewardResourceAmounts), victoryValue);
		}
		return cards;
	}
}
