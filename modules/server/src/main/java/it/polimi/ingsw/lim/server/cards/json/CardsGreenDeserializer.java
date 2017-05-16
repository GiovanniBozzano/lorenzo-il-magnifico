package it.polimi.ingsw.lim.server.cards.json;

import com.google.gson.*;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardTerritory;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.cards.CardsDeck;

import java.lang.reflect.Type;

public class CardsGreenDeserializer implements JsonDeserializer<CardsDeck<DevelopmentCardTerritory>>
{
	@Override
	public CardsDeck<DevelopmentCardTerritory> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
	{
		DevelopmentCardTerritory[] periodFirst = CardsGreenDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_first").getAsJsonArray(), Period.FIRST);
		DevelopmentCardTerritory[] periodSecond = CardsGreenDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_second").getAsJsonArray(), Period.SECOND);
		DevelopmentCardTerritory[] periodThird = CardsGreenDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_third").getAsJsonArray(), Period.THIRD);
		return new CardsDeck<>(periodFirst, periodSecond, periodThird);
	}

	private static DevelopmentCardTerritory[] getPeriodCards(JsonArray periodArray, Period period)
	{
		DevelopmentCardTerritory[] cards = new DevelopmentCardTerritory[8];
		for (int index = 0; index < periodArray.size() && index < 8; index++) {
			JsonObject card = periodArray.get(index).getAsJsonObject();
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
			cards[index] = new DevelopmentCardTerritory(displayName, period, instantResources, activationCost, harvestResources);
		}
		return cards;
	}
}
