package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public class CardGreen extends DevelopmentCard
{
	/*
	AVAMPOSTO_COMMERCIALE("GREEN_CARD_1_1", Period.FIRST, new ResourceAmount[] {}, 1, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1) }),
	BOSCO("GREEN_CARD_1_2", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1) }, 2, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1) }),
	BORGO("GREEN_CARD_1_3", Period.FIRST, new ResourceAmount[] {}, 3, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.SERVANT, 1) }),
	CAVA_DI_GHIAIA("GREEN_CARD_1_4", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 2) }, 4, new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 2) }),
	FORESTA("GREEN_CARD_1_5", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1) }, 5, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 3) }),
	MONASTERO("GREEN_CARD_1_6", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.SERVANT, 1) }, 6, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1), new ResourceAmount(ResourceType.STONE, 1) }),
	ROCCA("GREEN_CARD_1_7", Period.FIRST, new ResourceAmount[] {}, 5, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.STONE, 1) }),
	CITTA("GREEN_CARD_1_8", Period.FIRST, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 3) }, 6, new ResourceAmount[] { new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1) }),
	MINIERA_ORO("GREEN_CARD_2_1", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1) }, 1, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 2) }),
	VILLAGGIO_MONTANO("GREEN_CARD_2_2", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 1) }, 3, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 1), new ResourceAmount(ResourceType.WOOD, 2) }),
	VILLAGGIO_MINERARIO("GREEN_CARD_2_3", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 2), new ResourceAmount(ResourceType.STONE, 1) }, 4, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 1), new ResourceAmount(ResourceType.STONE, 2) }),
	CAVA_DI_PIETRA("GREEN_CARD_2_4", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 1) }, 3, new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 3) }),
	POSSEDIMENTO("GREEN_CARD_2_5", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 2), new ResourceAmount(ResourceType.WOOD, 1) }, 4, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.WOOD, 2) }),
	EREMO("GREEN_CARD_2_6", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1) }, 2, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1) }),
	MANIERO("GREEN_CARD_2_7", Period.SECOND, new ResourceAmount[] {}, 5, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.SERVANT, 2) }),
	DUCATO("GREEN_CARD_2_8", Period.SECOND, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 4) }, 6, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.WOOD, 2) }),
	CITTA_MERCANTILE("GREEN_CARD_3_1", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1), 1, new ResourceAmount(ResourceType.SERVANT, 1) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 3) }),
	TENUTA("GREEN_CARD_3_2", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 1), new ResourceAmount(ResourceType.WOOD, 1) }, 3, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 2), new ResourceAmount(ResourceType.WOOD, 2) }),
	COLONIA("GREEN_CARD_3_3", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 2) }, 5, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 4), new ResourceAmount(ResourceType.WOOD, 1) }),
	CAVA_DI_MARMO("GREEN_CARD_3_4", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 3) }, 2, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 1), new ResourceAmount(ResourceType.STONE, 2) }),
	PROVINCIA("GREEN_CARD_3_5", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1), new ResourceAmount(ResourceType.STONE, 1) }, 6, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 4), new ResourceAmount(ResourceType.STONE, 1) }),
	SANTUARIO("GREEN_CARD_3_6", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1) }, 1, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.FAITH_POINT, 1) }),
	CASTELLO("GREEN_CARD_3_7", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 2), new ResourceAmount(ResourceType.COIN, 2) }, 4, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 3), new ResourceAmount(ResourceType.SERVANT, 1) }),
	CITTA_FORTIFICATA("GREEN_CARD_3_8", Period.THIRD, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.SERVANT, 1) }, 2, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 1), new ResourceAmount(ResourceType.SERVANT, 2) }),;
	*/
	private final String displayName;
	private final Period period;
	private final ResourceAmount[] instantResources;
	private final int activationCost;
	private final ResourceAmount[] harvestResources;

	public CardGreen(String displayName, Period period, ResourceAmount[] instantResources, int activationCost, ResourceAmount[] harvestResources)
	{
		this.displayName = displayName;
		this.period = period;
		this.instantResources = instantResources;
		this.activationCost = activationCost;
		this.harvestResources = harvestResources;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}

	public Period getPeriod()
	{
		return this.period;
	}

	public ResourceAmount[] getInstantResources()
	{
		return this.instantResources;
	}

	public int getActivationCost()
	{
		return this.activationCost;
	}

	public ResourceAmount[] getHarvestResources()
	{
		return this.harvestResources;
	}
}

