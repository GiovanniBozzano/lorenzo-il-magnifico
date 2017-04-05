package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public enum CardYellow
{
	ZECCA("YELLOW_CARD_1_1", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 5) }, 5),
	ESATTORIA("YELLOW_CARD_1_2", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 3), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 5) }, 5),
	ARCO_TRIONFO("YELLOW_CARD_1_3", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 6) }, 6),
	TEATRO("YELLOW_CARD_1_4", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 6) }, 6),
	FALEGNAMERIA("YELLOW_CARD_1_5", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1), new ResourceAmount(Resource.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }, 4),
	TAGLIAPIETRE("YELLOW_CARD_1_6", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2) }, 3),
	CAPPELLA("YELLOW_CARD_1_7", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1) }, 2),
	RESIDENZA("YELLOW_CARD_1_8", Period.FIRST, new ResourceAmount[] { new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 1) }, 1),
	MERCATO("YELLOW_CARD_2_1", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }, 3),
	TESORERIA("YELLOW_CARD_2_2", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 4) }, 3),
	GILDA_PITTORI("YELLOW_CARD_2_3", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 5) }, 4),
	GILDA_SCULTORI("YELLOW_CARD_2_4", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 6) }, 5),
	GILDA_COSTRUTTORI("YELLOW_CARD_2_5", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 4) }, 4),
	BATTISTERO("YELLOW_CARD_2_6", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2), new ResourceAmount(Resource.FAITH_POINT, 1) }, 2),
	CASERMA("YELLOW_CARD_2_7", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }, 1),
	FORTEZZA("YELLOW_CARD_2_8", Period.SECOND, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 8) }, 6),
	BANCA("YELLOW_CARD_3_1", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 3), new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 7) }, 2),
	FIERA("YELLOW_CARD_3_2", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 4), new ResourceAmount(Resource.WOOD, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 8) }, 4),
	GIARDINO("YELLOW_CARD_3_3", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 2), new ResourceAmount(Resource.WOOD, 4), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 10) }, 1),
	CASTELLETTO("YELLOW_CARD_3_4", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 9) }, 5),
	PALAZZO("YELLOW_CARD_3_5", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 3), new ResourceAmount(Resource.WOOD, 3), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 9) }, 6),
	BASILICA("YELLOW_CARD_3_6", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 5), new ResourceAmount(Resource.FAITH_POINT, 1) }, 1),
	ACCADEMIA_MILITARE("YELLOW_CARD_3_7", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 1), new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 7) }, 3),
	CATTEDRALE("YELLOW_CARD_3_8", Period.THIRD, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 4), new ResourceAmount(Resource.STONE, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 7), new ResourceAmount(Resource.FAITH_POINT, 3) }, 2);
	private String displayName;
	private Period period;
	private ResourceAmount[] buildResources;
	private ResourceAmount[] instantResources;
	private int activationCost;

	CardYellow(String displayName, Period period, ResourceAmount[] buildResources, ResourceAmount[] instantResources, int activatioCost)
	{
		this.displayName = displayName;
		this.period = period;
		this.buildResources = buildResources;
		this.instantResources = instantResources;
		this.activationCost = activatioCost;
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
}
