package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.CardAmount;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.List;

public abstract class LeaderCardInformations extends CardInformations
{
	private final List<LeaderCardConditionsOption> conditionsOptions;
	private final String description;

	LeaderCardInformations(String texturePath, String displayName, String description, List<LeaderCardConditionsOption> conditionsOptions)
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
				stringBuilder.append(ResourceAmount.getResourcesInformations(leaderCardConditionsOption.getResourceAmounts(), true));
			}
			if (!leaderCardConditionsOption.getCardAmounts().isEmpty()) {
				stringBuilder.append("\nRequired cards:");
				stringBuilder.append(CardAmount.getCardsInformations(leaderCardConditionsOption.getCardAmounts(), true));
			}
			stringBuilder.append("\n==============");
		}
		stringBuilder.append("\n\n");
		return stringBuilder.toString();
	}
}
