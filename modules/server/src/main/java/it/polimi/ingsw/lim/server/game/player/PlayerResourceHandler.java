package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerResourceHandler
{
	private static final Map<Integer, Integer> TERRITORY_SLOTS_CONDITIONS = new HashMap<>();

	static {
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(0, 0);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(1, 0);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(2, 3);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(3, 7);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(4, 12);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(5, 18);
	}

	private static final Map<Period, Integer> EXCOMMUNICATION_CONDITIONS = new EnumMap<>(Period.class);

	static {
		PlayerResourceHandler.EXCOMMUNICATION_CONDITIONS.put(Period.FIRST, 3);
		PlayerResourceHandler.EXCOMMUNICATION_CONDITIONS.put(Period.SECOND, 4);
		PlayerResourceHandler.EXCOMMUNICATION_CONDITIONS.put(Period.THIRD, 5);
	}

	public static final Map<Integer, Integer> FAITH_POINTS_PRICES = new HashMap<>();

	static {
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(0, 0);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(1, 1);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(2, 2);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(3, 3);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(4, 4);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(5, 5);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(6, 7);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(7, 9);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(8, 11);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(9, 13);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(10, 15);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(11, 17);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(12, 19);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(13, 22);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(14, 25);
		PlayerResourceHandler.FAITH_POINTS_PRICES.put(15, 30);
	}

	private final Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Integer> temporaryResources = new EnumMap<>(ResourceType.class);

	PlayerResourceHandler(int resourcesServant, int resourcesStone, int resourcesWood)
	{
		this.resources.put(ResourceType.COIN, 0);
		this.resources.put(ResourceType.COUNCIL_PRIVILEGE, 0);
		this.resources.put(ResourceType.FAITH_POINT, 0);
		this.resources.put(ResourceType.MILITARY_POINT, 0);
		this.resources.put(ResourceType.PRESTIGE_POINT, 0);
		this.resources.put(ResourceType.SERVANT, resourcesServant);
		this.resources.put(ResourceType.STONE, resourcesStone);
		this.resources.put(ResourceType.VICTORY_POINT, 0);
		this.resources.put(ResourceType.WOOD, resourcesWood);
		this.temporaryResources.put(ResourceType.COIN, 0);
		this.temporaryResources.put(ResourceType.COUNCIL_PRIVILEGE, 0);
		this.temporaryResources.put(ResourceType.FAITH_POINT, 0);
		this.temporaryResources.put(ResourceType.MILITARY_POINT, 0);
		this.temporaryResources.put(ResourceType.PRESTIGE_POINT, 0);
		this.temporaryResources.put(ResourceType.SERVANT, 0);
		this.temporaryResources.put(ResourceType.STONE, 0);
		this.temporaryResources.put(ResourceType.VICTORY_POINT, 0);
		this.temporaryResources.put(ResourceType.WOOD, 0);
	}

	public void addResource(ResourceType resourceType, int amount)
	{
		this.resources.put(resourceType, this.resources.get(resourceType) + amount);
		this.fixResourcesCap();
	}

	public void addResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount());
		}
		this.fixResourcesCap();
	}

	private void fixResourcesCap()
	{
		if (this.resources.get(ResourceType.FAITH_POINT) > 25) {
			this.resources.put(ResourceType.FAITH_POINT, 25);
		}
		if (this.resources.get(ResourceType.FAITH_POINT) > 15) {
			this.resources.put(ResourceType.FAITH_POINT, 15);
		}
		if (this.resources.get(ResourceType.PRESTIGE_POINT) > 9) {
			this.resources.put(ResourceType.PRESTIGE_POINT, 9);
		}
	}

	public void addTemporaryResource(ResourceType resourceType, int amount)
	{
		this.temporaryResources.put(resourceType, this.temporaryResources.get(resourceType) + amount);
		this.fixTemporaryResourcesCap();
	}

	public void addTemporaryResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			this.temporaryResources.put(resourceAmount.getResourceType(), this.temporaryResources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount());
		}
		this.fixTemporaryResourcesCap();
	}

	private void fixTemporaryResourcesCap()
	{
		if (this.temporaryResources.get(ResourceType.FAITH_POINT) > 25) {
			this.temporaryResources.put(ResourceType.FAITH_POINT, 25);
		}
		if (this.temporaryResources.get(ResourceType.FAITH_POINT) > 15) {
			this.temporaryResources.put(ResourceType.FAITH_POINT, 15);
		}
		if (this.temporaryResources.get(ResourceType.PRESTIGE_POINT) > 9) {
			this.temporaryResources.put(ResourceType.PRESTIGE_POINT, 9);
		}
	}

	public void subtractResource(ResourceType resourceType, int amount)
	{
		this.resources.put(resourceType, this.resources.get(resourceType) - amount);
	}

	public void subtractResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount());
		}
	}

	public void subtractTemporaryResource(ResourceType resourceType, int amount)
	{
		this.temporaryResources.put(resourceType, this.resources.get(resourceType) - amount);
	}

	public void subtractTemporaryResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			this.temporaryResources.put(resourceAmount.getResourceType(), this.temporaryResources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount());
		}
	}

	public boolean isTerritorySlotAvailable(int territorySlot)
	{
		return PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.containsKey(territorySlot) && this.resources.get(ResourceType.MILITARY_POINT) >= PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.get(territorySlot);
	}

	public boolean isExcommunicated(Period period)
	{
		return this.resources.get(ResourceType.FAITH_POINT) < PlayerResourceHandler.EXCOMMUNICATION_CONDITIONS.get(period);
	}

	public void resetFaithPoints()
	{
		this.resources.put(ResourceType.FAITH_POINT, 0);
	}

	public int convertToVictoryPoints()
	{
		return (this.resources.get(ResourceType.COIN) + this.resources.get(ResourceType.WOOD) + this.resources.get(ResourceType.STONE) + this.resources.get(ResourceType.SERVANT)) / 5;
	}

	public Map<ResourceType, Integer> getResources()
	{
		return this.resources;
	}

	public Map<ResourceType, Integer> getTemporaryResources()
	{
		return this.temporaryResources;
	}
}
