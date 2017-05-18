package it.polimi.ingsw.lim.server.cards.json;

import com.google.gson.*;
import it.polimi.ingsw.lim.common.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.ResourceAmountMultiplierCard;
import it.polimi.ingsw.lim.common.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.server.cards.DevelopmentCardsDeck;

import java.lang.reflect.Type;

public class DevelopmentCardsBuildingDeserializer implements JsonDeserializer<DevelopmentCardsDeck<DevelopmentCardBuilding>>
{
	@Override
	public DevelopmentCardsDeck<DevelopmentCardBuilding> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
	{
		DevelopmentCardBuilding[] periodFirst = DevelopmentCardsBuildingDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_first").getAsJsonArray(), Period.FIRST);
		DevelopmentCardBuilding[] periodSecond = DevelopmentCardsBuildingDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_second").getAsJsonArray(), Period.SECOND);
		DevelopmentCardBuilding[] periodThird = DevelopmentCardsBuildingDeserializer.getPeriodCards(jsonElement.getAsJsonObject().get("period_third").getAsJsonArray(), Period.THIRD);
		return new DevelopmentCardsDeck<>(periodFirst, periodSecond, periodThird);
	}

	private static DevelopmentCardBuilding[] getPeriodCards(JsonArray periodArray, Period period)
	{
		DevelopmentCardBuilding[] cards = new DevelopmentCardBuilding[8];
		for (int index = 0; index < periodArray.size() && index < 8; index++) {
			JsonObject card = periodArray.get(index).getAsJsonObject();
			String displayName = card.get("display_name").getAsString();
			JsonArray buildResourcesJson = card.get("build_resources").getAsJsonArray();
			ResourceAmount[] buildResources = new ResourceAmount[buildResourcesJson.size()];
			for (int indexBuildResources = 0; indexBuildResources < buildResourcesJson.size(); indexBuildResources++) {
				JsonObject buildResource = buildResourcesJson.get(indexBuildResources).getAsJsonObject();
				buildResources[indexBuildResources] = new ResourceAmount(ResourceType.valueOf(buildResource.get("type").getAsString()), buildResource.get("amount").getAsInt());
			}
			JsonArray instantResourcesJson = card.get("instant_resources").getAsJsonArray();
			ResourceAmount[] instantResources = new ResourceAmount[instantResourcesJson.size()];
			for (int indexInstantResources = 0; indexInstantResources < instantResourcesJson.size(); indexInstantResources++) {
				JsonObject instantResource = instantResourcesJson.get(indexInstantResources).getAsJsonObject();
				instantResources[indexInstantResources] = new ResourceAmount(ResourceType.valueOf(instantResource.get("type").getAsString()), instantResource.get("amount").getAsInt());
			}
			int activationCost = card.get("activation_cost").getAsInt();
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
			cards[index] = new DevelopmentCardBuilding(displayName, period, buildResources, instantResources, activationCost, resourceTradeOptions);
		}
		return cards;
	}
}
