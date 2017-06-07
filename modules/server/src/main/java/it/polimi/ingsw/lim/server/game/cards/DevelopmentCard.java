package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCard extends Card
{
	private List<ResourceCostOption> resourceCostOptions;
	private Reward reward;

	DevelopmentCard(String displayName, int index, List<ResourceCostOption> resourceCostOptions, Reward reward)
	{
		super(displayName, index);
		this.resourceCostOptions = new ArrayList<>(resourceCostOptions);
		this.reward = reward;
	}

	public List<ResourceCostOption> getResourceCostOptions()
	{
		return this.resourceCostOptions;
	}

	public Reward getReward()
	{
		return this.reward;
	}
}
