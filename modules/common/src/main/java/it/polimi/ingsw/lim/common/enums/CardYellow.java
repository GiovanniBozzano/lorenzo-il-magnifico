package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.ResourceTradeOption;

public enum CardYellow
{
	ZECCA("YELLOW_CARD_1_1", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 5) }, 5, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1, CardType.YELLOW) }) }),
	ESATTORIA("YELLOW_CARD_1_2", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 3), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 5) }, 5, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1, CardType.GREEN) }) }),
	ARCO_TRIONFO("YELLOW_CARD_1_3", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 6) }, 6, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 1, CardType.PURPLE) }) }),
	TEATRO("YELLOW_CARD_1_4", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 6) }, 6, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 1, CardType.BLUE) }) }),
	FALEGNAMERIA("YELLOW_CARD_1_5", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1), new ResourceAmount(Resource.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }, 4, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 5) }) }),
	TAGLIAPIETRE("YELLOW_CARD_1_6", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2) }, 3, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 5) }) }),
	CAPPELLA("YELLOW_CARD_1_7", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1) }, 2, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1) }) }),
	RESIDENZA("YELLOW_CARD_1_8", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 1) }, 1, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.COUNCIL_PRIVILEGE, 1) }) }),
	MERCATO("YELLOW_CARD_2_1", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }, 3, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.COIN, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.STONE, 2) }) }),
	TESORERIA("YELLOW_CARD_2_2", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 4) }, 3, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 5) }) }),
	GILDA_PITTORI("YELLOW_CARD_2_3", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 5) }, 4, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 7) }) }),
	GILDA_SCULTORI("YELLOW_CARD_2_4", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 6) }, 5, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 7) }) }),
	GILDA_COSTRUTTORI("YELLOW_CARD_2_5", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 4) }, 4, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 1), new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 6) }) }),
	BATTISTERO("YELLOW_CARD_2_6", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2), new ResourceAmount(Resource.FAITH_POINT, 1) }, 2, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.VICTORY_POINT, 2) }) }),
	CASERMA("YELLOW_CARD_2_7", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }, 1, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 3) }) }),
	FORTEZZA("YELLOW_CARD_2_8", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 8) }, 6, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 2), new ResourceAmount(Resource.VICTORY_POINT, 2) }) }),
	BANCA("YELLOW_CARD_3_1", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 3), new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 7) }, 2, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 5) }) }),
	FIERA("YELLOW_CARD_3_2", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 4), new ResourceAmount(Resource.WOOD, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 8) }, 4, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.COIN, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 3), new ResourceAmount(Resource.STONE, 3) }) }),
	GIARDINO("YELLOW_CARD_3_3", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 2), new ResourceAmount(Resource.WOOD, 4), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 10) }, 1, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }) }),
	CASTELLETTO("YELLOW_CARD_3_4", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 9) }, 5, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2), new ResourceAmount(Resource.COUNCIL_PRIVILEGE, 1) }) }),
	PALAZZO("YELLOW_CARD_3_5", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 3), new ResourceAmount(Resource.WOOD, 3), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 9) }, 6, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 2), new ResourceAmount(Resource.VICTORY_POINT, 4) }) }),
	BASILICA("YELLOW_CARD_3_6", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 5), new ResourceAmount(Resource.FAITH_POINT, 1) }, 1, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 2) }), new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 2) }) }),
	ACCADEMIA_MILITARE("YELLOW_CARD_3_7", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 1), new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 7) }, 3, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 3), new ResourceAmount(Resource.VICTORY_POINT, 1) }) }),
	CATTEDRALE("YELLOW_CARD_3_8", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 4), new ResourceAmount(Resource.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 7), new ResourceAmount(Resource.FAITH_POINT, 3) }, 2, new ResourceTradeOption[] { new ResourceTradeOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 1) }) });
	private String displayName;
	private Period period;
	private ResourceAmount[] buildResources;
	private ResourceAmount[] instantResources;
	private int activationCost;
	private ResourceTradeOption[] tradeOptions;

	CardYellow(String displayName, Period period, ResourceAmount[] buildResources, ResourceAmount[] instantResources, int activationCost, ResourceTradeOption[] tradeOptions)
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
		return displayName;
	}

	public Period getPeriod()
	{
		return period;
	}

	public ResourceAmount[] getBuildResources()
	{
		return buildResources;
	}

	public ResourceAmount[] getInstantResources()
	{
		return instantResources;
	}

	public int getActivationCost()
	{
		return activationCost;
	}

	public ResourceTradeOption[] getTradeOptions()
	{
		return tradeOptions;
	}
}
