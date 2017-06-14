package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;

import java.util.List;

public class DevelopmentCardVentureInformations extends DevelopmentCardInformations
{
	private final int victoryValue;

	public DevelopmentCardVentureInformations(int index, String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, int victoryValue)
	{
		super(index, displayName, texturePath, CardType.VENTURE, resourceCostOptions);
		this.victoryValue = victoryValue;
	}

	public int getVictoryValue()
	{
		return this.victoryValue;
	}
}
