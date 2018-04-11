package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardBuildingInformation extends DevelopmentCardInformation
{
	private final int activationValue;
	private final List<ResourceTradeOption> resourceTradeOptions;

	public DevelopmentCardBuildingInformation(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformation reward, int activationValue, List<ResourceTradeOption> resourceTradeOptions)
	{
		super(displayName, texturePath, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.resourceTradeOptions = new ArrayList<>(resourceTradeOptions);
	}

	@Override
	public String getInformation()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getCommonInformation());
		stringBuilder.append("\n\nPRODUCTION ACTIVATION COST: ");
		stringBuilder.append(this.activationValue);
		if (!this.resourceTradeOptions.isEmpty()) {
			stringBuilder.append("\n\nRESOURCE TRADE OPTIONS:\n==============");
			for (ResourceTradeOption resourceTradeOption : this.resourceTradeOptions) {
				stringBuilder.append(resourceTradeOption.getInformation(true));
				stringBuilder.append("\n==============");
			}
		}
		return stringBuilder.toString();
	}
}
