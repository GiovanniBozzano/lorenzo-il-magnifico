package it.polimi.ingsw.lim.server.game.board;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public enum PersonalBonusTile
{
	PERSONAL_BONUS_TILES_0(0, "/images/personal_bonus_tiles/personal_bonus_tile_0.png", "/images/player_boards/player_board_0.png", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.SERVANT, 1), new ResourceAmount(ResourceType.COIN, 2))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.MILITARY_POINT, 1)))),
	PERSONAL_BONUS_TILES_1(1, "/images/personal_bonus_tiles/personal_bonus_tile_1.png", "/images/player_boards/player_board_1.png", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.SERVANT, 1), new ResourceAmount(ResourceType.MILITARY_POINT, 2))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.COIN, 1)))),
	PERSONAL_BONUS_TILES_2(2, "/images/personal_bonus_tiles/personal_bonus_tile_2.png", "/images/player_boards/player_board_2.png", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.COIN, 1))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.SERVANT, 1)))),
	PERSONAL_BONUS_TILES_3(3, "/images/personal_bonus_tiles/personal_bonus_tile_3.png", "/images/player_boards/player_board_3.png", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.SERVANT, 2), new ResourceAmount(ResourceType.COIN, 1))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.MILITARY_POINT, 1)))),
	PERSONAL_BONUS_TILES_4(4, "/images/personal_bonus_tiles/personal_bonus_tile_4.png", "/images/player_boards/player_board_4.png", 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.MILITARY_POINT, 1), new ResourceAmount(ResourceType.SERVANT, 2))), 1, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.WOOD, 1))));
	private final int index;
	private final String texturePath;
	private final String playerBoardTexturePath;
	private final int productionActivationCost;
	private final List<ResourceAmount> productionInstantResources;
	private final int harvestActivationCost;
	private final List<ResourceAmount> harvestInstantResources;

	PersonalBonusTile(int index, String texturePath, String playerBoardTexturePath, int productionActivationCost, List<ResourceAmount> productionInstantResources, int harvestActivationCost, List<ResourceAmount> harvestInstantResources)
	{
		this.index = index;
		this.texturePath = texturePath;
		this.playerBoardTexturePath = playerBoardTexturePath;
		this.productionActivationCost = productionActivationCost;
		this.productionInstantResources = productionInstantResources;
		this.harvestActivationCost = harvestActivationCost;
		this.harvestInstantResources = harvestInstantResources;
	}

	public static PersonalBonusTile fromIndex(int index)
	{
		for (PersonalBonusTile personalBonusTile : PersonalBonusTile.values()) {
			if (personalBonusTile.index == index) {
				return personalBonusTile;
			}
		}
		throw new NoSuchElementException();
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public String getPlayerBoardTexturePath()
	{
		return this.playerBoardTexturePath;
	}

	public int getProductionActivationCost()
	{
		return this.productionActivationCost;
	}

	public List<ResourceAmount> getProductionInstantResources()
	{
		return this.productionInstantResources;
	}

	public int getHarvestActivationCost()
	{
		return this.harvestActivationCost;
	}

	public List<ResourceAmount> getHarvestInstantResources()
	{
		return this.harvestInstantResources;
	}
}
