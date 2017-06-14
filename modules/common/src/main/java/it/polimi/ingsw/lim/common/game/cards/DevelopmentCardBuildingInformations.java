package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardBuildingInformations extends DevelopmentCardInformations
{
	private final int activationValue;
	private final List<ResourceTradeOption> resourceTradeOptions;

	public DevelopmentCardBuildingInformations(int index, String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, int activationValue, List<ResourceTradeOption> resourceTradeOptions)
	{
		super(index, displayName, texturePath, CardType.BUILDING, resourceCostOptions);
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
