package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformation;

import java.util.List;

public class DevelopmentCardCharacterInformation extends DevelopmentCardInformation
{
	private final String modifierInformation;

	public DevelopmentCardCharacterInformation(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformation reward, String modifierInformation)
	{
		super(displayName, texturePath, resourceCostOptions, reward);
		this.modifierInformation = modifierInformation;
	}

	@Override
	public String getInformation()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getCommonInformation());
		if (this.modifierInformation != null) {
			stringBuilder.append("\n\nMODIFIER:\n| ");
			stringBuilder.append(this.modifierInformation.replace("\n", "\n| "));
		}
		return stringBuilder.toString();
	}
}
