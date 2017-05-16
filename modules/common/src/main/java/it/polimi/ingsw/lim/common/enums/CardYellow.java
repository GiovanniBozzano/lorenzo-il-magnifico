package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.ResourceTradeOption;

public class CardYellow
{
	/*
	ZECCA("YELLOW_CARD_1_1", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 5) }, 5, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1, CardType.YELLOW) }) }),
	ESATTORIA("YELLOW_CARD_1_2", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 3), new ResourceAmount(ResourceType.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 5) }, 5, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1, CardType.GREEN) }) }),
	ARCO_TRIONFO("YELLOW_CARD_1_3", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 2), new ResourceAmount(ResourceType.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 6) }, 6, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 1, CardType.PURPLE) }) }),
	TEATRO("YELLOW_CARD_1_4", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 2), new ResourceAmount(ResourceType.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 6) }, 6, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 1, CardType.BLUE) }) }),
	FALEGNAMERIA("YELLOW_CARD_1_5", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 3) }, 4, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 5) }) }),
	TAGLIAPIETRE("YELLOW_CARD_1_6", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 2) }, 3, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 5) }) }),
	CAPPELLA("YELLOW_CARD_1_7", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1) }, 2, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1) }) }),
	RESIDENZA("YELLOW_CARD_1_8", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 1) }, 1, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1) }) }),
	MERCATO("YELLOW_CARD_2_1", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 2), new ResourceAmount(ResourceType.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 3) }, 3, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 3) }, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 2), new ResourceAmount(ResourceType.STONE, 2) }) }),
	TESORERIA("YELLOW_CARD_2_2", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 3) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 4) }, 3, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 5) }) }),
	GILDA_PITTORI("YELLOW_CARD_2_3", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 4) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 5) }, 4, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 3) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 7) }) }),
	GILDA_SCULTORI("YELLOW_CARD_2_4", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 6) }, 5, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 7) }) }),
	GILDA_COSTRUTTORI("YELLOW_CARD_2_5", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 4) }, 4, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 1), new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 6) }) }),
	BATTISTERO("YELLOW_CARD_2_6", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 2), new ResourceAmount(ResourceType.FAITH_POINT, 1) }, 2, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 2), new ResourceAmount(ResourceType.VICTORY_POINT, 2) }) }),
	CASERMA("YELLOW_CARD_2_7", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 3) }, 1, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 3) }) }),
	FORTEZZA("YELLOW_CARD_2_8", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 2), new ResourceAmount(ResourceType.WOOD, 2), new ResourceAmount(ResourceType.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 8) }, 6, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.VICTORY_POINT, 2) }) }),
	BANCA("YELLOW_CARD_3_1", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 3), new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 7) }, 2, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 5) }) }),
	FIERA("YELLOW_CARD_3_2", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 4), new ResourceAmount(ResourceType.WOOD, 3) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 8) }, 4, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 4) }, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 3), new ResourceAmount(ResourceType.STONE, 3) }) }),
	GIARDINO("YELLOW_CARD_3_3", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 2), new ResourceAmount(ResourceType.WOOD, 4), new ResourceAmount(ResourceType.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 10) }, 1, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 3) }) }),
	CASTELLETTO("YELLOW_CARD_3_4", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 2), new ResourceAmount(ResourceType.WOOD, 2), new ResourceAmount(ResourceType.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 9) }, 5, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 2), new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1) }) }),
	PALAZZO("YELLOW_CARD_3_5", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 3), new ResourceAmount(ResourceType.WOOD, 3), new ResourceAmount(ResourceType.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 9) }, 6, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 2), new ResourceAmount(ResourceType.VICTORY_POINT, 4) }) }),
	BASILICA("YELLOW_CARD_3_6", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 5), new ResourceAmount(ResourceType.FAITH_POINT, 1) }, 1, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 2) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 2) }) }),
	ACCADEMIA_MILITARE("YELLOW_CARD_3_7", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 1), new ResourceAmount(ResourceType.WOOD, 2), new ResourceAmount(ResourceType.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 7) }, 3, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 3), new ResourceAmount(ResourceType.VICTORY_POINT, 1) }) }),
	CATTEDRALE("YELLOW_CARD_3_8", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 4), new ResourceAmount(ResourceType.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 7), new ResourceAmount(ResourceType.FAITH_POINT, 3) }, 2, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 1) }) });
	*/
	private final String displayName;
	private final Period period;
	private final ResourceAmount[] buildResources;
	private final ResourceAmount[] instantResources;
	private final int activationCost;
	private final ResourceTradeOption[] tradeOptions;

	public CardYellow(String displayName, Period period, ResourceAmount[] buildResources, ResourceAmount[] instantResources, int activationCost, ResourceTradeOption[] tradeOptions)
	{
		this.displayName = displayName;
		this.period = period;
		this.buildResources = buildResources;
		this.instantResources = instantResources;
		this.activationCost = activationCost;
		this.tradeOptions = tradeOptions;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}

	public Period getPeriod()
	{
		return this.period;
	}

	public ResourceAmount[] getBuildResources()
	{
		return this.buildResources;
	}

	public ResourceAmount[] getInstantResources()
	{
		return this.instantResources;
	}

	public int getActivationCost()
	{
		return this.activationCost;
	}

	public ResourceTradeOption[] getTradeOptions()
	{
		return this.tradeOptions;
	}
}
