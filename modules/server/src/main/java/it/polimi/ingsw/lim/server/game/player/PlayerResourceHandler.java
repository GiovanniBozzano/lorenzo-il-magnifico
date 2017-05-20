package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;

import java.util.EnumMap;
import java.util.Map;

public class PlayerResourceHandler
{
	private final PlayerInformations playerInformations;
	private final Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);

	public PlayerResourceHandler(PlayerInformations playerInformations, int resourcesCoin, int resourcesWood, int resourcesStone, int resourcesServant)
	{
		this.playerInformations = playerInformations;
		this.resources.put(ResourceType.COIN, resourcesCoin);
		this.resources.put(ResourceType.WOOD, resourcesWood);
		this.resources.put(ResourceType.STONE, resourcesStone);
		this.resources.put(ResourceType.SERVANT, resourcesServant);
		this.resources.put(ResourceType.MILITARY_POINT, 0);
		this.resources.put(ResourceType.FAITH_POINT, 0);
		this.resources.put(ResourceType.VICTORY_POINT, 0);
	}

	public void addResources(ResourceAmount[] resourcesAmount)
	{
		for (ResourceAmount resourceAmount : resourcesAmount) {
			this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount());
		}
	}

	public void subtractResources(ResourceAmount[] resourcesAmount)
	{
		for (ResourceAmount resourceAmount : resourcesAmount) {
			this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount());
		}
	}

	public boolean isTerritorySlotAvailable(int territorySlot)
	{
		switch (territorySlot) {
			case 0:
			case 1:
				return true;
			case 2:
				return this.resources.get(ResourceType.MILITARY_POINT) > 3;
			case 3:
				return this.resources.get(ResourceType.MILITARY_POINT) > 3;
			case 4:
				return this.resources.get(ResourceType.MILITARY_POINT) > 3;
			case 5:
				return this.resources.get(ResourceType.MILITARY_POINT) > 3;
			default:
				return false;
		}
	}

	public boolean isExcommunicated(Period period)
	{
		switch (period) {
			case FIRST:
				return this.resources.get(ResourceType.FAITH_POINT) < 3;
			case SECOND:
				return this.resources.get(ResourceType.FAITH_POINT) < 4;
			case THIRD:
				return this.resources.get(ResourceType.FAITH_POINT) < 5;
			default:
				return true;
		}
	}

	public int convertToVictoryPoints()
	{
		return (this.resources.get(ResourceType.COIN) + this.resources.get(ResourceType.WOOD) + this.resources.get(ResourceType.STONE) + this.resources.get(ResourceType.SERVANT)) / 5;
	}

	public int getResourcesCoin()
	{
		return this.resources.get(ResourceType.COIN);
	}

	public int getResourcesWood()
	{
		return this.resources.get(ResourceType.WOOD);
	}

	public int getResourcesStone()
	{
		return this.resources.get(ResourceType.STONE);
	}

	public int getResourcesServant()
	{
		return this.resources.get(ResourceType.SERVANT);
	}

	public int getResourcesMilitaryPoint()
	{
		return this.resources.get(ResourceType.MILITARY_POINT);
	}

	public int getResourcesFaithPoint()
	{
		return this.resources.get(ResourceType.FAITH_POINT);
	}

	public int getResourcesVictoryPoint()
	{
		return this.resources.get(ResourceType.VICTORY_POINT);
	}

	public PlayerInformations getPlayerInformations()
	{
		return this.playerInformations;
	}
}
