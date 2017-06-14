package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class DevelopmentCardVenture extends DevelopmentCard
{
	private final int victoryValue;

	public DevelopmentCardVenture(int index, String texturePath, String displayName, List<ResourceCostOption> resourceCostOptions, Reward reward, int victoryValue)
	{
		super(index, texturePath, displayName, CardType.VENTURE, resourceCostOptions, reward);
		this.victoryValue = victoryValue;
	}

	public int getVictoryValue()
	{
		return this.victoryValue;
	}
}
