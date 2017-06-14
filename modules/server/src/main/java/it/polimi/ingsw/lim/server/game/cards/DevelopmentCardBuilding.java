package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class DevelopmentCardBuilding extends DevelopmentCard
{
	private final int activationValue;
	private final List<ResourceTradeOption> resourceTradeOptions;

	public DevelopmentCardBuilding(int index, String displayName, List<ResourceCostOption> resourceCostOptions, Reward reward, int activationValue, List<ResourceTradeOption> resourceTradeOptions)
	{
		super(index, displayName, CardType.BUILDING, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.resourceTradeOptions = resourceTradeOptions;
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
