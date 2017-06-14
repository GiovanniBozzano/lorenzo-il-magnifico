package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

import java.util.ArrayList;
import java.util.List;

public abstract class DevelopmentCardInformations extends CardInformations
{
	private final CardType cardType;
	private final List<ResourceCostOption> resourceCostOptions;
	private final RewardInformations reward;

	public DevelopmentCardInformations(int index, String texturePath, String displayName, CardType cardType, List<ResourceCostOption> resourceCostOptions, RewardInformations reward)
	{
		super(index, texturePath, displayName);
		this.cardType = cardType;
		this.resourceCostOptions = new ArrayList<>(resourceCostOptions);
		this.reward = reward;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public List<ResourceCostOption> getResourceCostOptions()
	{
		return this.resourceCostOptions;
	}

	public RewardInformations getReward()
	{
		return this.reward;
	}
}
