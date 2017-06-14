package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardTerritoryInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class DevelopmentCardTerritory extends DevelopmentCard
{
	private final int activationValue;
	private final List<ResourceAmount> harvestResources;

	public DevelopmentCardTerritory(int index, String texturePath, String displayName, List<ResourceCostOption> resourceCostOptions, Reward reward, int activationValue, List<ResourceAmount> harvestResources)
	{
		super(index, texturePath, displayName, CardType.TERRITORY, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.harvestResources = harvestResources;
	}

	@Override
	public DevelopmentCardInformations getInformations()
	{
		return new DevelopmentCardTerritoryInformations(this.getIndex(), this.getTexturePath(), this.getDisplayName(), this.getResourceCostOptions(), new RewardInformations(this.getReward().getActionReward().getDescription(), this.getReward().getResourceAmounts()), this.activationValue, this.harvestResources);
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

