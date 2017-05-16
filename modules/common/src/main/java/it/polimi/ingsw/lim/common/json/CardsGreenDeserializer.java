package it.polimi.ingsw.lim.common.json;

import com.google.gson.*;
import it.polimi.ingsw.lim.common.enums.CardGreen;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.CardsDeck;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;

import java.lang.reflect.Type;

public class CardsGreenDeserializer implements JsonDeserializer<CardsDeck<CardGreen>>
{
	@Override
	public CardsDeck<CardGreen> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
	{
		CardGreen[] periodFirst = CardsGreenDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_first").getAsJsonArray());
		CardGreen[] periodSecond = CardsGreenDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_second").getAsJsonArray());
		CardGreen[] periodThird = CardsGreenDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_third").getAsJsonArray());
		return new CardsDeck<>(periodFirst, periodSecond, periodThird);
	}

	private static CardGreen[] getPeriodCards(JsonArray period)
	{
		CardGreen[] cards = new CardGreen[8];
		for (int index = 0; index < period.size() && index < 8; index++) {
			JsonObject card = period.get(index).getAsJsonObject();
			String displayName = card.get("display_name").getAsString();
			JsonArray instantResourcesJson = card.get("instant_resources").getAsJsonArray();
			ResourceAmount[] instantResources = new ResourceAmount[instantResourcesJson.size()];
			for (int indexInstantResources = 0; indexInstantResources < instantResourcesJson.size(); indexInstantResources++) {
				JsonObject instantResource = instantResourcesJson.get(indexInstantResources).getAsJsonObject();
				instantResources[indexInstantResources] = new ResourceAmount(ResourceType.valueOf(instantResource.get("type").getAsString()), instantResource.get("amount").getAsInt());
			}
			int activationCost = card.get("activation_cost").getAsInt();
			JsonArray harvestResourcesJson = card.get("instant_resources").getAsJsonArray();
			ResourceAmount[] harvestResources = new ResourceAmount[harvestResourcesJson.size()];
			for (int indexHarvestResources = 0; indexHarvestResources < harvestResourcesJson.size(); indexHarvestResources++) {
				JsonObject harvestResource = harvestResourcesJson.get(indexHarvestResources).getAsJsonObject();
				harvestResources[indexHarvestResources] = new ResourceAmount(ResourceType.valueOf(harvestResource.get("type").getAsString()), harvestResource.get("amount").getAsInt());
			}
			cards[index] = new CardGreen(displayName, Period.FIRST, instantResources, activationCost, harvestResources);
		}
		return cards;
	}
}
