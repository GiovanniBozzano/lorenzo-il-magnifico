package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

import java.util.List;

public class DevelopmentCardCharacterInformations extends DevelopmentCardInformations
{
	private final String modifierInformations;

	public DevelopmentCardCharacterInformations(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformations reward, String modifierInformations)
	{
		super(displayName, texturePath, resourceCostOptions, reward);
		this.modifierInformations = modifierInformations;
	}

	@Override
	public String getInformations()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getCommonInformations());
		if (this.modifierInformations != null) {
			stringBuilder.append("\n\nMODIFIER:\n| ");
			stringBuilder.append(this.modifierInformations.replace("\n", "\n| "));
		}
		return stringBuilder.toString();
	}
}
