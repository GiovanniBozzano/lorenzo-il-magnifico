package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;

import java.util.List;

public class ActionInformationsChooseRewardPickDevelopmentCard extends ActionInformations
{
	private final int servants;
	private final CardType cardType;
	private final Row row;
	private final Row instantRewardRow;
	private final List<ResourceAmount> instantDiscountChoice;
	private final List<ResourceAmount> discountChoice;
	private final ResourceCostOption resourceCostOption;

	public ActionInformationsChooseRewardPickDevelopmentCard(int servants, CardType cardType, Row row, Row instantRewardRow, List<ResourceAmount> instantDiscountChoice, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption)
	{
		super(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD);
		this.servants = servants;
		this.cardType = cardType;
		this.row = row;
		this.instantRewardRow = instantRewardRow;
		this.instantDiscountChoice = instantDiscountChoice;
		this.discountChoice = discountChoice;
		this.resourceCostOption = resourceCostOption;
	}

	public int getServants()
	{
		return this.servants;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public Row getRow()
	{
		return this.row;
	}

	public Row getInstantRewardRow()
	{
		return this.instantRewardRow;
	}

	public List<ResourceAmount> getInstantDiscountChoice()
	{
		return this.instantDiscountChoice;
	}

	public List<ResourceAmount> getDiscountChoice()
	{
		return this.discountChoice;
	}

	public ResourceCostOption getResourceCostOption()
	{
		return this.resourceCostOption;
	}
}
