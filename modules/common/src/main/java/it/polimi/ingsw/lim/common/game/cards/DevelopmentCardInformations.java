package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardInformations extends CardInformations
{
	private final CardType cardType;
	private final List<ResourceCostOption> resourceCostOptions;

	DevelopmentCardInformations(int index, String displayName, String texturePath, CardType cardType, List<ResourceCostOption> resourceCostOptions)
	{
		super(index, displayName, texturePath);
		this.cardType = cardType;
		this.resourceCostOptions = new ArrayList<>(resourceCostOptions);
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public List<ResourceCostOption> getResourceCostOptions()
	{
		return this.resourceCostOptions;
	}
}
