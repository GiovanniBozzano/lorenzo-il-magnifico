package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardBuildingInformations extends DevelopmentCardInformations
{
	private final int activationValue;
	private final List<ResourceTradeOption> resourceTradeOptions;

	public DevelopmentCardBuildingInformations(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformations reward, int activationValue, List<ResourceTradeOption> resourceTradeOptions)
	{
		super(displayName, texturePath, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.resourceTradeOptions = new ArrayList<>(resourceTradeOptions);
	}

	@Override
	public String getInformations()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getCommonInformations());
		stringBuilder.append("\n\nPRODUCTION ACTIVATION COST: ");
		stringBuilder.append(this.activationValue);
		if (!this.resourceTradeOptions.isEmpty()) {
			stringBuilder.append("\n\nRESOURCE TRADE OPTIONS:\n==============");
			for (ResourceTradeOption resourceTradeOption : this.resourceTradeOptions) {
				stringBuilder.append(resourceTradeOption.getInformations(false));
				stringBuilder.append("\n==============");
			}
		}
		return stringBuilder.toString();
	}
}
