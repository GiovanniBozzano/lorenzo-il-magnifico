package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;

import java.util.ArrayList;
import java.util.List;

public class AvailableActionPickDevelopmentCard extends AvailableActionFamilyMember
{
	private final CardType cardType;
	private final Row row;
	private final List<ResourceCostOption> resourceCostOptions;
	private final List<List<ResourceAmount>> discountChoices;

	public AvailableActionPickDevelopmentCard(FamilyMemberType familyMemberType, CardType cardType, Row row, List<ResourceCostOption> resourceCostOptions, List<List<ResourceAmount>> discountChoices)
	{
		super(familyMemberType);
		this.cardType = cardType;
		this.row = row;
		this.resourceCostOptions = new ArrayList<>(resourceCostOptions);
		this.discountChoices = new ArrayList<>(discountChoices);
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public Row getRow()
	{
		return this.row;
	}

	public List<ResourceCostOption> getResourceCostOptions()
	{
		return this.resourceCostOptions;
	}

	public List<List<ResourceAmount>> getDiscountChoices()
	{
		return this.discountChoices;
	}
}