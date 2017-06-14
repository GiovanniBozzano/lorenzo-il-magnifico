package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;

import java.util.List;

public class DevelopmentCardCharacterInformations extends DevelopmentCardInformations
{
	private final String permanentBonusInformations;

	public DevelopmentCardCharacterInformations(int index, String displayName, String texturePath, List<ResourceCostOption> resourceCostOptions, String permanentBonusInformations)
	{
		super(index, displayName, texturePath, CardType.CHARACTER, resourceCostOptions);
		this.permanentBonusInformations = permanentBonusInformations;
	}

	public String getPermanentBonusInformations()
	{
		return this.permanentBonusInformations;
	}
}
