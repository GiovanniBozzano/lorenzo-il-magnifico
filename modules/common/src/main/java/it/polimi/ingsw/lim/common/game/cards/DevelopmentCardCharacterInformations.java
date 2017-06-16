package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;

import java.util.List;

public class DevelopmentCardCharacterInformations extends DevelopmentCardInformations
{
	private final String modifierInformations;

	public DevelopmentCardCharacterInformations(String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, RewardInformations reward, String modifierInformations)
	{
		super(displayName, texturePath, CardType.CHARACTER, resourceCostOptions, reward);
		this.modifierInformations = modifierInformations;
	}

	public String getModifierInformations()
	{
		return this.modifierInformations;
	}
}
