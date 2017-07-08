package it.polimi.ingsw.lim.common.game.utils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>This class represents a single "Leader Card play" conditions option.
 */
public class LeaderCardConditionsOption implements Serializable
{
	private final List<DevelopmentCardAmount> developmentCardAmounts;
	private final List<ResourceAmount> resourceAmounts;

	public LeaderCardConditionsOption(List<DevelopmentCardAmount> developmentCardAmounts, List<ResourceAmount> resourceAmounts)
	{
		this.developmentCardAmounts = developmentCardAmounts;
		this.resourceAmounts = resourceAmounts;
	}

	public List<DevelopmentCardAmount> getDevelopmentCardAmounts()
	{
		return this.developmentCardAmounts;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}

	public String getInformation()
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (!this.resourceAmounts.isEmpty()) {
			stringBuilder.append("\nRequired resources:\n");
			stringBuilder.append(ResourceAmount.getResourcesInformation(this.resourceAmounts, true));
		}
		if (!this.developmentCardAmounts.isEmpty()) {
			stringBuilder.append("\nRequired cards:\n");
			stringBuilder.append(DevelopmentCardAmount.getDevelopmentCardsInformation(this.developmentCardAmounts, true));
		}
		return stringBuilder.toString();
	}
}
