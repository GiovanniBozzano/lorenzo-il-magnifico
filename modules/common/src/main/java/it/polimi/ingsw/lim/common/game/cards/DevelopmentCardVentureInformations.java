package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

import java.util.List;

public class DevelopmentCardVentureInformations extends DevelopmentCardInformations
{
	private final int victoryValue;

	public DevelopmentCardVentureInformations(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformations reward, int victoryValue)
	{
		super(displayName, texturePath, CardType.VENTURE, resourceCostOptions, reward);
		this.victoryValue = victoryValue;
	}

	@Override
	public String getInformations()
	{
		return this.getDevelopmentCardCommonInformations() + "\n\nVICTORY VALUE: " + this.victoryValue;
	}

	public int getVictoryValue()
	{
		return this.victoryValue;
	}
}
