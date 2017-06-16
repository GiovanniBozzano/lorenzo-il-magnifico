package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardBuildingInformations extends DevelopmentCardInformations
{
	private final int activationValue;
	private final List<ResourceTradeOption> resourceTradeOptions;

	public DevelopmentCardBuildingInformations(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformations reward, int activationValue, List<ResourceTradeOption> resourceTradeOptions)
	{
		super(displayName, texturePath, CardType.BUILDING, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.resourceTradeOptions = new ArrayList<>(resourceTradeOptions);
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
