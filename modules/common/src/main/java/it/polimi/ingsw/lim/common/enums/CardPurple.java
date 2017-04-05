package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.events.Event;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.Reward;

public enum CardPurple
{
	INGAGGIARE_RECLUTE("PURPLE_CARD_1_1", Period.FIRST, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.COIN,4)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 4),
	RIPARARE_CHIESA("PURPLE_CARD_1_2", Period.FIRST, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.COIN,1), new ResourceAmount(Resource.WOOD,1), new ResourceAmount(Resource.STONE,1)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 5),
	COSTRUIRE_MURA("PURPLE_CARD_1_3", Period.FIRST, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.STONE,3)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 3),
	INNALZARE_STATUA("PURPLE_CARD_1_4", Period.FIRST, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.WOOD,2), new ResourceAmount(Resource.STONE,2)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 4),
	CAMPAGNA_MILITARE("PURPLE_CARD_1_5", Period.FIRST, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{new ResourceAmount(Resource.MILITARY_POINT,3)}, new ResourceAmount[] {new ResourceAmount(Resource.MILITARY_POINT,2)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 5),
	OSPITARE_MENDICANTI("PURPLE_CARD_1_6", Period.FIRST, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.WOOD,3)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 4),
	COMBATTERE_ERESIE("PURPLE_CARD_1_7", Period.FIRST, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{new ResourceAmount(Resource.MILITARY_POINT,5)}, new ResourceAmount[] {new ResourceAmount(Resource.MILITARY_POINT,3)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 5),
	SOSTEGNO_VESCOVO("PURPLE_CARD_1_8", Period.FIRST, new ResourceCostOption[] { new ResourceCostOption(new ResourceAmount[] {new ResourceAmount(Resource.MILITARY_POINT, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 2)}), new ResourceCostOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.STONE, 1) }) }, new Reward(new Event[] {}, new ResourceAmount[] {}), 1),
	INGAGGIARE_SOLDATI("PURPLE_CARD_2_1", Period.SECOND, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.COIN,6)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 5),
	RIPARARE_ABBAZIA("PURPLE_CARD_2_2", Period.SECOND, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.COIN,2), new ResourceAmount(Resource.WOOD,2), new ResourceAmount(Resource.STONE,2)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 6),
	COSTRUIRE_BASTIONI("PURPLE_CARD_2_3", Period.SECOND, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.STONE,4)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 2),
	SUPPORTO_RE("PURPLE_CARD_2_4", Period.SECOND, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{new ResourceAmount(Resource.MILITARY_POINT,6)}, new ResourceAmount[] {new ResourceAmount(Resource.MILITARY_POINT,3)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 3),
	SCAVARE_CANALIZZAZIONI("PURPLE_CARD_2_5", Period.SECOND, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.SERVANT,2), new ResourceAmount(Resource.COIN,3)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 5),
	ACCOGLIERE_STRANIERI("PURPLE_CARD_2_6", Period.SECOND, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.WOOD,4)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 4),
	CROCIATA("PURPLE_CARD_2_7", Period.SECOND, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{new ResourceAmount(Resource.MILITARY_POINT,8)}, new ResourceAmount[] {new ResourceAmount(Resource.MILITARY_POINT,4)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 5),
	SOSTEGNO_CARDINALE("PURPLE_CARD_2_8", Period.SECOND, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[] {new ResourceAmount(Resource.MILITARY_POINT, 7) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 4)}), new ResourceCostOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 2), new ResourceAmount(Resource.COIN, 3), new ResourceAmount(Resource.STONE, 2) }) }, new Reward(new Event[] {}, new ResourceAmount[] {}), 4),
	INGAGGIARE_MERCENARI("PURPLE_CARD_3_1", Period.THIRD, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.COIN,8)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 6),
	RIPARARE_CATTEDRALE("PURPLE_CARD_3_2", Period.THIRD, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.COIN,3), new ResourceAmount(Resource.WOOD,3), new ResourceAmount(Resource.STONE,3)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 5),
	COSTRUIRE_TORRI("PURPLE_CARD_3_3", Period.THIRD, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.STONE,6)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 4),
	COMMISSIONARE_ARTE_SACRA("PURPLE_CARD_3_4", Period.THIRD, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.WOOD,6)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 3),
	CONQUISTA_MILITARE("PURPLE_CARD_3_5", Period.THIRD, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{new ResourceAmount(Resource.MILITARY_POINT,12)}, new ResourceAmount[] {new ResourceAmount(Resource.MILITARY_POINT,6)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 7),
	MIGLIORARE_STRADE("PURPLE_CARD_3_6", Period.THIRD, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{}, new ResourceAmount[] {new ResourceAmount(Resource.SERVANT,3), new ResourceAmount(Resource.COIN,4)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 5),
	GUERRA_SANTA("PURPLE_CARD_3_7", Period.THIRD, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[]{new ResourceAmount(Resource.MILITARY_POINT,15)}, new ResourceAmount[] {new ResourceAmount(Resource.MILITARY_POINT,8)})}, new Reward(new Event[] {}, new ResourceAmount[] {}), 8),
	SOSTEGNO_PAPA("PURPLE_CARD_3_8", Period.THIRD, new ResourceCostOption[] {new ResourceCostOption(new ResourceAmount[] {new ResourceAmount(Resource.MILITARY_POINT, 10) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 5)}), new ResourceCostOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 3), new ResourceAmount(Resource.COIN, 4), new ResourceAmount(Resource.STONE, 3) }) }, new Reward(new Event[] {}, new ResourceAmount[] {}), 10),;
	private String displayName;
	private Period period;
	private ResourceCostOption[] resourceCostOptions;
	private Reward instantReward;
	private int victoryValue;

	CardPurple(String displayName, Period period, ResourceCostOption[] resourceCostOptions, Reward instantReward, int victoryValue)
	{
		this.displayName = displayName;
		this.period = period;
		this.resourceCostOptions = resourceCostOptions;
		this.instantReward = instantReward;
		this.victoryValue = victoryValue;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public Period getPeriod()
	{
		return period;
	}

	public ResourceCostOption[] getResourceCostOptions() {return resourceCostOptions;}

	public Reward getInstantReward() {return instantReward;}

	public int getVictoryValue() {return victoryValue;}
}
