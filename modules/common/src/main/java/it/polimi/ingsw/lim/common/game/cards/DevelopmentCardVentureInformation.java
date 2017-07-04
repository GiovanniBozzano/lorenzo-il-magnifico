package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;

import java.util.List;

public class DevelopmentCardVentureInformation extends DevelopmentCardInformation
{
	private final int victoryValue;

	public DevelopmentCardVentureInformation(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformation reward, int victoryValue)
	{
		super(displayName, texturePath, resourceCostOptions, reward);
		this.victoryValue = victoryValue;
	}

	@Override
	public String getInformation()
	{
		return this.getCommonInformation() + "\n\nVICTORY VALUE: " + this.victoryValue;
	}
}
