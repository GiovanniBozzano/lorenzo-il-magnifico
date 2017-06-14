package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.List;

public class DevelopmentCardCharacter extends DevelopmentCard
{
	private final Object permanentBonus;

	public DevelopmentCardCharacter(int index, String texturePath, String displayName, List<ResourceCostOption> resourceCostOptions, Reward reward, Object permanentBonus)
	{
		super(index, texturePath, displayName, CardType.CHARACTER, resourceCostOptions, reward);
		this.permanentBonus = permanentBonus;
	}

	public Object getPermanentBonus()
	{
		return this.permanentBonus;
	}
}
