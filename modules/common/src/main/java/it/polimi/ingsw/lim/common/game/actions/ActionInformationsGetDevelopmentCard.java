package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;

import java.util.List;

public class ActionInformationsGetDevelopmentCard extends ActionInformations
{
	private final FamilyMemberType familyMemberType;
	private final int servants;
	private final CardType cardType;
	private final Row row;
	private final List<ResourceAmount> discountChoice;
	private ResourceCostOption resourceCostOption;

	public ActionInformationsGetDevelopmentCard(FamilyMemberType familyMemberType, int servants, CardType cardType, Row row, List<ResourceAmount> discountChoice, ResourceCostOption resourceCostOption)
	{
		super(ActionType.GET_DEVELOPMENT_CARD);
		this.familyMemberType = familyMemberType;
		this.servants = servants;
		this.cardType = cardType;
		this.row = row;
		this.discountChoice = discountChoice;
		this.resourceCostOption = resourceCostOption;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
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

	public List<ResourceAmount> getDiscountChoice()
	{
		return this.discountChoice;
	}

	public ResourceCostOption getResourceCostOption()
	{
		return this.resourceCostOption;
	}
}
