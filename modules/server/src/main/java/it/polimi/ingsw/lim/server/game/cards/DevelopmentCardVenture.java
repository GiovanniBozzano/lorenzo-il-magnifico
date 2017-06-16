package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardVentureInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;
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

	@Override
	public DevelopmentCardVentureInformations getInformations()
	{
		return new DevelopmentCardVentureInformations(this.getTexturePath(), this.getDisplayName(), this.getResourceCostOptions(), this.getReward() == null ? null : new RewardInformations(this.getReward().getActionReward() == null ? null : this.getReward().getActionReward().getDescription(), this.getReward().getResourceAmounts()), this.victoryValue);
	}

	public int getVictoryValue()
	{
		return this.victoryValue;
	}
}
