package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardBuildingInformations extends DevelopmentCardInformations
{
	private final int activationValue;
	private final List<ResourceTradeOption> resourceTradeOptions;

	public DevelopmentCardBuildingInformations(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformations reward, int activationValue, List<ResourceTradeOption> resourceTradeOptions)
	{
		super(displayName, texturePath, CardType.BUILDING, resourceCostOptions, reward);
		this.activationValue = activationValue;
		this.resourceTradeOptions = new ArrayList<>(resourceTradeOptions);
	}

	@Override
	public String getInformations()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getDevelopmentCardCommonInformations());
		stringBuilder.append("\n\nPRODUCTION ACTIVATION COST: ");
		stringBuilder.append(this.activationValue);
		if (!this.resourceTradeOptions.isEmpty()) {
			stringBuilder.append("\n\nRESOURCE TRADE OPTIONS:");
			for (ResourceTradeOption resourcetradeOption : this.resourceTradeOptions) {
				stringBuilder.append("\n==============");
				if (!resourcetradeOption.getEmployedResources().isEmpty()) {
					stringBuilder.append("\nEmployed resources:");
					for (ResourceAmount resourceAmount : resourcetradeOption.getEmployedResources()) {
						stringBuilder.append("\n    - ");
						stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
				}
				if (!resourcetradeOption.getProducedResources().isEmpty()) {
					stringBuilder.append("\nProduced resources:");
					for (ResourceAmount resourceAmount : resourcetradeOption.getProducedResources()) {
						stringBuilder.append("\n    - ");
						stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
				}
				stringBuilder.append("\n==============");
			}
		}
		return stringBuilder.toString();
	}

	public int getActivationValue()
	{
		return this.activationValue;
	}

	public List<ResourceTradeOption> getResourceTradeOptions()
	{
		return this.resourceTradeOptions;
	}
}
