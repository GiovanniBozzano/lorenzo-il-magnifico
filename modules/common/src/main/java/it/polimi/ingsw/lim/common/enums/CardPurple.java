package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.events.Event;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.Reward;

public enum CardPurple
{
	INGAGGIARE_RECLUTE("PURPLE_CARD_1_1", Period.FIRST, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	RIPARARE_CHIESA("PURPLE_CARD_1_2", Period.FIRST, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	COSTRUIRE_MURA("PURPLE_CARD_1_3", Period.FIRST, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	INNALZARE_STATUA("PURPLE_CARD_1_4", Period.FIRST, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	CAMPAGNA_MILITARE("PURPLE_CARD_1_5", Period.FIRST, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	OSPITARE_MENDICANTI("PURPLE_CARD_1_6", Period.FIRST, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	COMBATTERE_ERESIE("PURPLE_CARD_1_7", Period.FIRST, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	SOSTEGNO_VESCOVO("PURPLE_CARD_1_8", Period.FIRST, new ResourceCostOption[] { new ResourceCostOption(new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 4) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 2) }), new ResourceCostOption(new ResourceAmount[] {}, new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.COIN, 2), new ResourceAmount(Resource.STONE, 1) }) }, new Reward(new Event[] {}, new ResourceAmount[] {})),
	INGAGGIARE_SOLDATI("PURPLE_CARD_2_1", Period.SECOND, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	RIPARARE_ABBAZIA("PURPLE_CARD_2_2", Period.SECOND, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	COSTRUIRE_BASTIONI("PURPLE_CARD_2_3", Period.SECOND, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	SUPPORTO_RE("PURPLE_CARD_2_4", Period.SECOND, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	SCAVARE_CANALIZZAZIONI("PURPLE_CARD_2_5", Period.SECOND, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	ACCOGLIERE_STRANIERI("PURPLE_CARD_2_6", Period.SECOND, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	CROCIATA("PURPLE_CARD_2_7", Period.SECOND, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	SOSTEGNO_CARDINALE("PURPLE_CARD_2_8", Period.SECOND, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	INGAGGIARE_MERCENARI("PURPLE_CARD_3_1", Period.THIRD, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	RIPARARE_CATTEDRALE("PURPLE_CARD_3_2", Period.THIRD, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	COSTRUIRE_TORRI("PURPLE_CARD_3_3", Period.THIRD, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	COMMISSIONARE_ARTE_SACRA("PURPLE_CARD_3_4", Period.THIRD, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	CONQUISTA_MILITARE("PURPLE_CARD_3_5", Period.THIRD, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	MIGLIORARE_STRADE("PURPLE_CARD_3_6", Period.THIRD, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	GUERRA_SANTA("PURPLE_CARD_3_7", Period.THIRD, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {})),
	SOSTEGNO_PAPA("PURPLE_CARD_3_8", Period.THIRD, new ResourceCostOption[] {}, new Reward(new Event[] {}, new ResourceAmount[] {}));
	private String displayName;
	private Period period;
	private ResourceCostOption[] resourceCostOptions;
	private Reward reward;

	CardPurple(String displayName, Period period, ResourceCostOption[] resourceCostOptions, Reward reward)
	{
		this.displayName = displayName;
		this.period = period;
		this.resourceCostOptions = resourceCostOptions;
		this.reward = reward;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public Period getPeriod()
	{
		return period;
	}

	public ResourceCostOption[] getResourceCostOptions()
	{
		return resourceCostOptions;
	}

	public Reward getReward()
	{
		return reward;
	}
}
