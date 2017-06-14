package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardCharacterInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.RewardInformations;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class DevelopmentCardCharacter extends DevelopmentCard
{
	private final Modifier modifier;

	public DevelopmentCardCharacter(int index, String texturePath, String displayName, List<ResourceCostOption> resourceCostOptions, Reward reward, Modifier modifier)
	{
		super(index, texturePath, displayName, CardType.CHARACTER, resourceCostOptions, reward);
		this.modifier = modifier;
	}

	@Override
	public DevelopmentCardInformations getInformations()
	{
		return new DevelopmentCardCharacterInformations(this.getIndex(), this.getTexturePath(), this.getDisplayName(), this.getResourceCostOptions(), new RewardInformations(this.getReward().getActionReward().getDescription(), this.getReward().getResourceAmounts()), this.modifier.getDescription());
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
}
