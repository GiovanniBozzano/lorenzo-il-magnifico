package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardTerritoryInformations extends DevelopmentCardInformations
{
	private final int activationValue;
	private final List<ResourceAmount> harvestResources;

	public DevelopmentCardTerritoryInformations(int index, String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformations reward, int activationValue, List<ResourceAmount> harvestResources)
	{
		super(index, displayName, texturePath, CardType.TERRITORY, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.harvestResources = new ArrayList<>(harvestResources);
	}

	public int getActivationValue()
	{
		return this.activationValue;
	}

	public List<ResourceAmount> getHarvestResources()
	{
		return this.harvestResources;
	}
}
