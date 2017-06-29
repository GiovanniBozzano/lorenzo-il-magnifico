package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.CardAmount;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class LeaderCardInformations extends CardInformations
{
	private final List<LeaderCardConditionsOption> conditionsOptions;
	private final String description;

	public LeaderCardInformations(String texturePath, String displayName, String description, List<LeaderCardConditionsOption> conditionsOptions)
	{
		super(texturePath, displayName);
		this.conditionsOptions = new ArrayList<>(conditionsOptions);
		this.description = description;
	}

	@Override
	String getCommonInformations()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getDisplayName());
		stringBuilder.append("\n\n");
		stringBuilder.append(this.description);
		stringBuilder.append("\n\nPLAY CONDITIONS:\n==============");
		for (LeaderCardConditionsOption leaderCardConditionsOption : this.conditionsOptions) {
			if (!leaderCardConditionsOption.getResourceAmounts().isEmpty()) {
				stringBuilder.append("\nRequired resources:");
				for (ResourceAmount resourceAmount : leaderCardConditionsOption.getResourceAmounts()) {
					stringBuilder.append("\n    - ");
					stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
			}
			if (!leaderCardConditionsOption.getCardAmounts().isEmpty()) {
				stringBuilder.append("\nRequired cards:");
				for (CardAmount cardAmount : leaderCardConditionsOption.getCardAmounts()) {
					stringBuilder.append("\n    - ");
					stringBuilder.append(CommonUtils.getCardTypesNames().get(cardAmount.getCardType()));
					stringBuilder.append(": ");
					stringBuilder.append(cardAmount.getAmount());
				}
			}
			stringBuilder.append("\n==============");
		}
		stringBuilder.append("\n\n");
		return stringBuilder.toString();
	}

	public List<LeaderCardConditionsOption> getConditionsOptions()
	{
		return this.conditionsOptions;
	}

	public String getDescription()
	{
		return this.description;
	}
}
