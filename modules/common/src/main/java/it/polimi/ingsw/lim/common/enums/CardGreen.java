package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public enum CardGreen
{
	AVAMPOSTO_COMMERCIALE("GREEN_CARD_1_1", Period.FIRST, 1, new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1) }),
	BOSCO("GREEN_CARD_1_2", Period.FIRST, 2, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1) }),
	BORGO("GREEN_CARD_1_3", Period.FIRST, 3, new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1), new ResourceAmount(Resource.SERVANT, 1) }),
	CAVA_DI_GHIAIA("GREEN_CARD_1_4", Period.FIRST, 4, new ResourceAmount[] { new ResourceAmount(Resource.STONE, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.STONE, 2) }),
	FORESTA("GREEN_CARD_1_5", Period.FIRST, 5, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 3) }),
	MONASTERO("GREEN_CARD_1_6", Period.FIRST, 6, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 2), new ResourceAmount(Resource.SERVANT, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1), new ResourceAmount(Resource.STONE, 1) }),
	ROCCA("GREEN_CARD_1_7", Period.FIRST, 5, new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 2), new ResourceAmount(Resource.STONE, 1) }),
	CITTA("GREEN_CARD_1_8", Period.FIRST, 6, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.COUNCIL_PRIVILEGE, 1) }),
	MINIERA_ORO("GREEN_CARD_2_1", Period.SECOND, 1, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2) }),
	VILLAGGIO_MONTANO("GREEN_CARD_2_2", Period.SECOND, 3, new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 1), new ResourceAmount(Resource.WOOD, 2) }),
	VILLAGGIO_MINERARIO("GREEN_CARD_2_3", Period.SECOND, 4, new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 2), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 1), new ResourceAmount(Resource.STONE, 2) }),
	CAVA_DI_PIETRA("GREEN_CARD_2_4", Period.SECOND, 3, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.STONE, 3) }),
	POSSEDIMENTO("GREEN_CARD_2_5", Period.SECOND, 4, new ResourceAmount[] { new ResourceAmount(Resource.SERVANT, 2), new ResourceAmount(Resource.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1), new ResourceAmount(Resource.WOOD, 2) }),
	EREMO("GREEN_CARD_2_6", Period.SECOND, 2, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1) }),
	MANIERO("GREEN_CARD_2_7", Period.SECOND, 5, new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 2), new ResourceAmount(Resource.SERVANT, 2) }),
	DUCATO("GREEN_CARD_2_8", Period.SECOND, 6, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1), new ResourceAmount(Resource.STONE, 1), new ResourceAmount(Resource.WOOD, 2) }),
	CITTA_MERCANTILE("GREEN_CARD_3_1", Period.THIRD, 1, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1), new ResourceAmount(Resource.SERVANT, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 3) }),
	TENUTA("GREEN_CARD_3_2", Period.THIRD, 3, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 1), new ResourceAmount(Resource.WOOD, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2), new ResourceAmount(Resource.WOOD, 2) }),
	COLONIA("GREEN_CARD_3_3", Period.THIRD, 5, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 4), new ResourceAmount(Resource.WOOD, 1) }),
	CAVA_DI_MARMO("GREEN_CARD_3_4", Period.THIRD, 2, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 3) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 1), new ResourceAmount(Resource.STONE, 2) }),
	PROVINCIA("GREEN_CARD_3_5", Period.THIRD, 6, new ResourceAmount[] { new ResourceAmount(Resource.COUNCIL_PRIVILEGE, 1), new ResourceAmount(Resource.STONE, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 4), new ResourceAmount(Resource.STONE, 1) }),
	SANTUARIO("GREEN_CARD_3_6", Period.THIRD, 1, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1), new ResourceAmount(Resource.FAITH_POINT, 1) }),
	CASTELLO("GREEN_CARD_3_7", Period.THIRD, 4, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2), new ResourceAmount(Resource.COIN, 2) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 3), new ResourceAmount(Resource.SERVANT, 1) }),
	CITTA_FORTIFICATA("GREEN_CARD_3_8", Period.THIRD, 2, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 2), new ResourceAmount(Resource.SERVANT, 1) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 1), new ResourceAmount(Resource.SERVANT, 2) }),;
	private String displayName;
	private Period period;
	private int activationCost;
	private ResourceAmount[] instantResources;
	private ResourceAmount[] harvestResources;

	CardGreen(String displayName, Period period, int activationCost, ResourceAmount[] instantResources, ResourceAmount[] harvestResources)
	{
		this.displayName = displayName;
		this.period = period;
		this.activationCost = activationCost;
		this.instantResources = instantResources;
		this.harvestResources = harvestResources;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public Period getPeriod()
	{
		return period;
	}

	public int getActivationCost()
	{
		return activationCost;
	}

	public ResourceAmount[] getInstantResources()
	{
		return instantResources;
	}

	public ResourceAmount[] getHarvestResources()
	{
		return harvestResources;
	}
}

