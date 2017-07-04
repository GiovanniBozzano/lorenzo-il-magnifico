package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardBuildingInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class DevelopmentCardBuilding extends DevelopmentCard
{
	private final int activationValue;
	private final List<ResourceTradeOption> resourceTradeOptions;

	public DevelopmentCardBuilding(int index, String texturePath, String displayName, List<ResourceCostOption> resourceCostOptions, Reward reward, int activationValue, List<ResourceTradeOption> resourceTradeOptions)
	{
		super(index, texturePath, displayName, CardType.BUILDING, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.resourceTradeOptions = resourceTradeOptions;
	}

	@Override
	public DevelopmentCardBuildingInformation getInformation()
	{
		return new DevelopmentCardBuildingInformation(this.getTexturePath(), this.getDisplayName(), this.getResourceCostOptions(), this.getReward() == null ? null : new RewardInformation(this.getReward().getActionReward() == null ? null : this.getReward().getActionReward().getDescription(), this.getReward().getResourceAmounts()), this.activationValue, this.resourceTradeOptions);
	}

	public int getActivationValue()
	{
		return this.activationValue;
	}

	public List<ResourceTradeOption> getResourceTradeOptions()
	{
		return this.resourceTradeOptions;
	}
}
