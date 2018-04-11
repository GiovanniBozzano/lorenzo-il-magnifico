package it.polimi.ingsw.lim.server.game.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmountMultiplierCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmountMultiplierResource;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

/**
 * <p>This class handles a player's resources information. It is used to store
 * game resources data concerning only this player.
 */
public class PlayerResourceHandler
{
	private static final Map<Integer, Integer> FAITH_POINTS_REWARDS = new FaithPointsRewardsBuilder("/json/faith_points_rewards.json").initialize();
	private final Player player;
	private final Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Integer> temporaryResources = new EnumMap<>(ResourceType.class);

	PlayerResourceHandler(Player player, int resourcesServant, int resourcesStone, int resourcesWood)
	{
		this.player = player;
		/*this.resources.put(ResourceType.COIN, 0);
		this.resources.put(ResourceType.COUNCIL_PRIVILEGE, 0);
		this.resources.put(ResourceType.FAITH_POINT, 0);
		this.resources.put(ResourceType.MILITARY_POINT, 0);
		this.resources.put(ResourceType.PRESTIGE_POINT, 0);
		this.resources.put(ResourceType.SERVANT, resourcesServant);
		this.resources.put(ResourceType.STONE, resourcesStone);
		this.resources.put(ResourceType.VICTORY_POINT, 0);
		this.resources.put(ResourceType.WOOD, resourcesWood);*/
		this.resources.put(ResourceType.COIN, 20);
		this.resources.put(ResourceType.COUNCIL_PRIVILEGE, 0);
		this.resources.put(ResourceType.FAITH_POINT, 5);
		this.resources.put(ResourceType.MILITARY_POINT, 0);
		this.resources.put(ResourceType.PRESTIGE_POINT, 0);
		this.resources.put(ResourceType.SERVANT, 20);
		this.resources.put(ResourceType.STONE, 20);
		this.resources.put(ResourceType.VICTORY_POINT, 35);
		this.resources.put(ResourceType.WOOD, 20);
		for (ResourceType resourceType : ResourceType.values()) {
			this.temporaryResources.put(resourceType, 0);
		}
	}

	public boolean canAffordResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			if (resourceAmount instanceof ResourceAmountMultiplierCard) {
				if (this.resources.get(resourceAmount.getResourceType()) < resourceAmount.getAmount() * this.player.getPlayerCardHandler().getDevelopmentCardsNumber(((ResourceAmountMultiplierCard) resourceAmount).getCardTypeMultiplier())) {
					return false;
				}
			} else if (resourceAmount instanceof ResourceAmountMultiplierResource) {
				if (this.resources.get(resourceAmount.getResourceType()) < resourceAmount.getAmount() * this.resources.get(((ResourceAmountMultiplierResource) resourceAmount).getResourceTypeMultiplier()) / ((ResourceAmountMultiplierResource) resourceAmount).getResourceAmountDivider()) {
					return false;
				}
			} else if (this.resources.get(resourceAmount.getResourceType()) < resourceAmount.getAmount()) {
				return false;
			}
		}
		return true;
	}

	public void addResource(ResourceType resourceType, int amount)
	{
		this.resources.put(resourceType, this.resources.get(resourceType) + amount);
		this.fixResourcesCap();
	}

	private void fixResourcesCap()
	{
		if (this.resources.get(ResourceType.FAITH_POINT) > 15) {
			this.resources.put(ResourceType.FAITH_POINT, 15);
		}
		if (this.resources.get(ResourceType.MILITARY_POINT) > 25) {
			this.resources.put(ResourceType.MILITARY_POINT, 25);
		}
		if (this.resources.get(ResourceType.PRESTIGE_POINT) > 9) {
			this.resources.put(ResourceType.PRESTIGE_POINT, 9);
		}
	}

	public void addTemporaryResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			if (resourceAmount instanceof ResourceAmountMultiplierCard) {
				this.temporaryResources.put(resourceAmount.getResourceType(), this.temporaryResources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount() * this.player.getPlayerCardHandler().getDevelopmentCardsNumber(((ResourceAmountMultiplierCard) resourceAmount).getCardTypeMultiplier()));
			} else if (resourceAmount instanceof ResourceAmountMultiplierResource) {
				this.temporaryResources.put(resourceAmount.getResourceType(), this.temporaryResources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount() * this.resources.get(((ResourceAmountMultiplierResource) resourceAmount).getResourceTypeMultiplier()) / ((ResourceAmountMultiplierResource) resourceAmount).getResourceAmountDivider());
			} else {
				this.temporaryResources.put(resourceAmount.getResourceType(), this.temporaryResources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount());
			}
		}
		this.fixTemporaryResourcesCap();
	}

	private void fixTemporaryResourcesCap()
	{
		if (this.temporaryResources.get(ResourceType.FAITH_POINT) > 15) {
			this.temporaryResources.put(ResourceType.FAITH_POINT, 15);
		}
		if (this.temporaryResources.get(ResourceType.MILITARY_POINT) > 25) {
			this.temporaryResources.put(ResourceType.MILITARY_POINT, 25);
		}
		if (this.temporaryResources.get(ResourceType.PRESTIGE_POINT) > 9) {
			this.temporaryResources.put(ResourceType.PRESTIGE_POINT, 9);
		}
	}

	public void convertTemporaryResources()
	{
		for (Entry<ResourceType, Integer> resource : this.temporaryResources.entrySet()) {
			this.addResource(resource.getKey(), resource.getValue());
		}
		for (ResourceType resourceType : ResourceType.values()) {
			this.temporaryResources.put(resourceType, 0);
		}
	}

	public void subtractResource(ResourceType resourceType, int amount)
	{
		this.resources.put(resourceType, this.resources.get(resourceType) - amount);
	}

	public void subtractResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			if (resourceAmount instanceof ResourceAmountMultiplierCard) {
				this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount() * this.player.getPlayerCardHandler().getDevelopmentCardsNumber(((ResourceAmountMultiplierCard) resourceAmount).getCardTypeMultiplier()));
			} else if (resourceAmount instanceof ResourceAmountMultiplierResource) {
				this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount() * this.resources.get(((ResourceAmountMultiplierResource) resourceAmount).getResourceTypeMultiplier()) / ((ResourceAmountMultiplierResource) resourceAmount).getResourceAmountDivider());
			} else {
				this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount());
			}
		}
	}

	public void resetFaithPoints()
	{
		this.resources.put(ResourceType.FAITH_POINT, 0);
	}

	public void resetVictoryPoints()
	{
		this.resources.put(ResourceType.VICTORY_POINT, 0);
	}

	public static Map<Integer, Integer> getFaithPointsPrices()
	{
		return PlayerResourceHandler.FAITH_POINTS_REWARDS;
	}

	public Map<ResourceType, Integer> getResources()
	{
		return this.resources;
	}

	public Map<ResourceType, Integer> getTemporaryResources()
	{
		return this.temporaryResources;
	}

	private static class FaithPointsRewardsBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = FaithPointsRewardsBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		FaithPointsRewardsBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Integer, Integer> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return FaithPointsRewardsBuilder.GSON.fromJson(reader, new TypeToken<Map<Integer, Integer>>()
				{
				}.getType());
			} catch (IOException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new HashMap<>();
		}
	}
}
