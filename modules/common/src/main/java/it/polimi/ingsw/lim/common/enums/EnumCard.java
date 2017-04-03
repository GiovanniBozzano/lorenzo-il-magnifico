package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.utils.CardAttribute;
import it.polimi.ingsw.lim.common.utils.CardPrice;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public enum EnumCard
{
	//////////////
	// 1 PERIOD //
	//////////////
	//   BLUE   //
	//////////////
	BLUE_CARD_1_1("BLUE_CARD_1_1", EnumPeriod.FIRST, EnumCardType.BLUE, new CardAttribute[] { new CardPrice().put(new ResourceAmount[] { new ResourceAmount(EnumResource.WOOD, 10), new ResourceAmount(EnumResource.STONE, 5) }) }),
	BLUE_CARD_1_2("BLUE_CARD_1_2", EnumCardType.BLUE),
	BLUE_CARD_1_3("BLUE_CARD_1_3", EnumCardType.BLUE),
	BLUE_CARD_1_4("BLUE_CARD_1_4", EnumCardType.BLUE),
	BLUE_CARD_1_5("BLUE_CARD_1_5", EnumCardType.BLUE),
	BLUE_CARD_1_6("BLUE_CARD_1_6", EnumCardType.BLUE),
	BLUE_CARD_1_7("BLUE_CARD_1_7", EnumCardType.BLUE),
	BLUE_CARD_1_8("BLUE_CARD_1_8", EnumCardType.BLUE),
	//////////////
	//  GREEN   //
	//////////////
	GREEN_CARD_1_1("GREEN_CARD_1_1", EnumCardType.GREEN),
	GREEN_CARD_1_2("GREEN_CARD_1_2", EnumCardType.GREEN),
	GREEN_CARD_1_3("GREEN_CARD_1_3", EnumCardType.GREEN),
	GREEN_CARD_1_4("GREEN_CARD_1_4", EnumCardType.GREEN),
	GREEN_CARD_1_5("GREEN_CARD_1_5", EnumCardType.GREEN),
	GREEN_CARD_1_6("GREEN_CARD_1_6", EnumCardType.GREEN),
	GREEN_CARD_1_7("GREEN_CARD_1_7", EnumCardType.GREEN),
	GREEN_CARD_1_8("GREEN_CARD_1_8", EnumCardType.GREEN),
	//////////////
	//  PURPLE  //
	//////////////
	PURPLE_CARD_1_1("PURPLE_CARD_1_1", EnumCardType.PURPLE),
	PURPLE_CARD_1_2("PURPLE_CARD_1_2", EnumCardType.PURPLE),
	PURPLE_CARD_1_3("PURPLE_CARD_1_3", EnumCardType.PURPLE),
	PURPLE_CARD_1_4("PURPLE_CARD_1_4", EnumCardType.PURPLE),
	PURPLE_CARD_1_5("PURPLE_CARD_1_5", EnumCardType.PURPLE),
	PURPLE_CARD_1_6("PURPLE_CARD_1_6", EnumCardType.PURPLE),
	PURPLE_CARD_1_7("PURPLE_CARD_1_7", EnumCardType.PURPLE),
	PURPLE_CARD_1_8("PURPLE_CARD_1_8", EnumCardType.PURPLE),
	//////////////
	//  YELLOW  //
	//////////////
	YELLOW_CARD_1_1("YELLOW_CARD_1_1", EnumCardType.YELLOW),
	YELLOW_CARD_1_2("YELLOW_CARD_1_2", EnumCardType.YELLOW),
	YELLOW_CARD_1_3("YELLOW_CARD_1_3", EnumCardType.YELLOW),
	YELLOW_CARD_1_4("YELLOW_CARD_1_4", EnumCardType.YELLOW),
	YELLOW_CARD_1_5("YELLOW_CARD_1_5", EnumCardType.YELLOW),
	YELLOW_CARD_1_6("YELLOW_CARD_1_6", EnumCardType.YELLOW),
	YELLOW_CARD_1_7("YELLOW_CARD_1_7", EnumCardType.YELLOW),
	YELLOW_CARD_1_8("YELLOW_CARD_1_8", EnumCardType.YELLOW),
	//////////////
	//////////////
	// 2 PERIOD //
	//////////////
	//   BLUE   //
	//////////////
	BLUE_CARD_2_1("BLUE_CARD_2_1", EnumCardType.BLUE),
	BLUE_CARD_2_2("BLUE_CARD_2_2", EnumCardType.BLUE),
	BLUE_CARD_2_3("BLUE_CARD_2_3", EnumCardType.BLUE),
	BLUE_CARD_2_4("BLUE_CARD_2_4", EnumCardType.BLUE),
	BLUE_CARD_2_5("BLUE_CARD_2_5", EnumCardType.BLUE),
	BLUE_CARD_2_6("BLUE_CARD_2_6", EnumCardType.BLUE),
	BLUE_CARD_2_7("BLUE_CARD_2_7", EnumCardType.BLUE),
	BLUE_CARD_2_8("BLUE_CARD_2_8", EnumCardType.BLUE),
	//////////////
	//  GREEN   //
	//////////////
	GREEN_CARD_2_1("GREEN_CARD_2_1", EnumCardType.GREEN),
	GREEN_CARD_2_2("GREEN_CARD_2_2", EnumCardType.GREEN),
	GREEN_CARD_2_3("GREEN_CARD_2_3", EnumCardType.GREEN),
	GREEN_CARD_2_4("GREEN_CARD_2_4", EnumCardType.GREEN),
	GREEN_CARD_2_5("GREEN_CARD_2_5", EnumCardType.GREEN),
	GREEN_CARD_2_6("GREEN_CARD_2_6", EnumCardType.GREEN),
	GREEN_CARD_2_7("GREEN_CARD_2_7", EnumCardType.GREEN),
	GREEN_CARD_2_8("GREEN_CARD_2_8", EnumCardType.GREEN),
	//////////////
	//  PURPLE  //
	//////////////
	PURPLE_CARD_2_1("PURPLE_CARD_2_1", EnumCardType.PURPLE),
	PURPLE_CARD_2_2("PURPLE_CARD_2_2", EnumCardType.PURPLE),
	PURPLE_CARD_2_3("PURPLE_CARD_2_3", EnumCardType.PURPLE),
	PURPLE_CARD_2_4("PURPLE_CARD_2_4", EnumCardType.PURPLE),
	PURPLE_CARD_2_5("PURPLE_CARD_2_5", EnumCardType.PURPLE),
	PURPLE_CARD_2_6("PURPLE_CARD_2_6", EnumCardType.PURPLE),
	PURPLE_CARD_2_7("PURPLE_CARD_2_7", EnumCardType.PURPLE),
	PURPLE_CARD_2_8("PURPLE_CARD_2_8", EnumCardType.PURPLE),
	//////////////
	//  YELLOW  //
	//////////////
	YELLOW_CARD_2_1("YELLOW_CARD_2_1", EnumCardType.YELLOW),
	YELLOW_CARD_2_2("YELLOW_CARD_2_2", EnumCardType.YELLOW),
	YELLOW_CARD_2_3("YELLOW_CARD_2_3", EnumCardType.YELLOW),
	YELLOW_CARD_2_4("YELLOW_CARD_2_4", EnumCardType.YELLOW),
	YELLOW_CARD_2_5("YELLOW_CARD_2_5", EnumCardType.YELLOW),
	YELLOW_CARD_2_6("YELLOW_CARD_2_6", EnumCardType.YELLOW),
	YELLOW_CARD_2_7("YELLOW_CARD_2_7", EnumCardType.YELLOW),
	YELLOW_CARD_2_8("YELLOW_CARD_2_8", EnumCardType.YELLOW),
	//////////////
	//////////////
	// 3 PERIOD //
	//////////////
	//   BLUE   //
	//////////////
	BLUE_CARD_3_1("BLUE_CARD_3_1", EnumCardType.BLUE),
	BLUE_CARD_3_2("BLUE_CARD_3_2", EnumCardType.BLUE),
	BLUE_CARD_3_3("BLUE_CARD_3_3", EnumCardType.BLUE),
	BLUE_CARD_3_4("BLUE_CARD_3_4", EnumCardType.BLUE),
	BLUE_CARD_3_5("BLUE_CARD_3_5", EnumCardType.BLUE),
	BLUE_CARD_3_6("BLUE_CARD_3_6", EnumCardType.BLUE),
	BLUE_CARD_3_7("BLUE_CARD_3_7", EnumCardType.BLUE),
	BLUE_CARD_3_8("BLUE_CARD_3_8", EnumCardType.BLUE),
	//////////////
	//  GREEN   //
	//////////////
	GREEN_CARD_3_1("GREEN_CARD_3_1", EnumCardType.GREEN),
	GREEN_CARD_3_2("GREEN_CARD_3_2", EnumCardType.GREEN),
	GREEN_CARD_3_3("GREEN_CARD_3_3", EnumCardType.GREEN),
	GREEN_CARD_3_4("GREEN_CARD_3_4", EnumCardType.GREEN),
	GREEN_CARD_3_5("GREEN_CARD_3_5", EnumCardType.GREEN),
	GREEN_CARD_3_6("GREEN_CARD_3_6", EnumCardType.GREEN),
	GREEN_CARD_3_7("GREEN_CARD_3_7", EnumCardType.GREEN),
	GREEN_CARD_3_8("GREEN_CARD_3_8", EnumCardType.GREEN),
	//////////////
	//  PURPLE  //
	//////////////
	PURPLE_CARD_3_1("PURPLE_CARD_3_1", EnumCardType.PURPLE),
	PURPLE_CARD_3_2("PURPLE_CARD_3_2", EnumCardType.PURPLE),
	PURPLE_CARD_3_3("PURPLE_CARD_3_3", EnumCardType.PURPLE),
	PURPLE_CARD_3_4("PURPLE_CARD_3_4", EnumCardType.PURPLE),
	PURPLE_CARD_3_5("PURPLE_CARD_3_5", EnumCardType.PURPLE),
	PURPLE_CARD_3_6("PURPLE_CARD_3_6", EnumCardType.PURPLE),
	PURPLE_CARD_3_7("PURPLE_CARD_3_7", EnumCardType.PURPLE),
	PURPLE_CARD_3_8("PURPLE_CARD_3_8", EnumCardType.PURPLE),
	//////////////
	//  YELLOW  //
	//////////////
	YELLOW_CARD_3_1("YELLOW_CARD_3_1", EnumCardType.YELLOW),
	YELLOW_CARD_3_2("YELLOW_CARD_3_2", EnumCardType.YELLOW),
	YELLOW_CARD_3_3("YELLOW_CARD_3_3", EnumCardType.YELLOW),
	YELLOW_CARD_3_4("YELLOW_CARD_3_4", EnumCardType.YELLOW),
	YELLOW_CARD_3_5("YELLOW_CARD_3_5", EnumCardType.YELLOW),
	YELLOW_CARD_3_6("YELLOW_CARD_3_6", EnumCardType.YELLOW),
	YELLOW_CARD_3_7("YELLOW_CARD_3_7", EnumCardType.YELLOW),
	YELLOW_CARD_3_8("YELLOW_CARD_3_8", EnumCardType.YELLOW);
	//////////////
	private String displayName;
	private EnumPeriod period;
	private EnumCardType type;
	private CardAttribute[] attributes;

	EnumCard(String displayName, EnumPeriod period, EnumCardType type, CardAttribute[] attributes)
	{
		this.displayName = displayName;
		this.period = period;
		this.type = type;
		this.attributes = attributes;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public EnumPeriod getPeriod()
	{
		return period;
	}

	public EnumCardType getType()
	{
		return type;
	}

	public CardAttribute[] getAttributes()
	{
		return attributes;
	}
}
