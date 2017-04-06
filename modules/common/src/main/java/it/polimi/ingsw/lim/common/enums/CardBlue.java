package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.events.Event;
import it.polimi.ingsw.lim.common.events.EventCard;
import it.polimi.ingsw.lim.common.events.EventWork;
import it.polimi.ingsw.lim.common.utils.*;

import java.util.ArrayList;

public enum CardBlue
{
	CONDOTTIERO("BLUE_CARD_1_1", Period.FIRST, 2, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 3) }), new BonusAdditionCard(2, CardType.GREEN, new ArrayList<>())),
	COSTRUTTORE("BLUE_CARD_1_2", Period.FIRST, 4, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionCard(2, CardType.YELLOW, new ArrayList<ResourceAmount[]>()
	{{
		add(new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1) });
		add(new ResourceAmount[] { new ResourceAmount(Resource.STONE, 1) });
	}})),
	DAMA("BLUE_CARD_1_3", Period.FIRST, 4, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionCard(2, CardType.BLUE, new ArrayList<ResourceAmount[]>()
	{{
		add(new ResourceAmount[] { new ResourceAmount(Resource.COIN, 1) });
	}})),
	CAVALIERE("BLUE_CARD_1_4", Period.FIRST, 2, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.COUNCIL_PRIVILEGE, 1) }), new BonusAdditionCard(2, CardType.PURPLE, new ArrayList<>())),
	CONTADINO("BLUE_CARD_1_5", Period.FIRST, 3, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionWork(2, Work.HARVEST)),
	ARTIGIANO("BLUE_CARD_1_6", Period.FIRST, 3, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionWork(2, Work.PRODUCTION)),
	PREDICATORE("BLUE_CARD_1_7", Period.FIRST, 2, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 4) }), new Malus(new Row[] { Row.THIRD, Row.FOURTH })),
	BADESSA("BLUE_CARD_1_8", Period.FIRST, 3, new Reward(new Event[] { new EventCard(4, new CardType[] { CardType.BLUE, CardType.GREEN, CardType.PURPLE, CardType.YELLOW }, new ArrayList<>()) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1) }), null),
	CAPITANO("BLUE_CARD_2_1", Period.FIRST, 4, new Reward(new Event[] { new EventCard(6, new CardType[] { CardType.GREEN }, new ArrayList<>()) }, new ResourceAmount[] { new ResourceAmount(Resource.MILITARY_POINT, 2) }), null),
	ARCHITETTO("BLUE_CARD_2_2", Period.SECOND, 4, new Reward(new Event[] { new EventCard(6, new CardType[] { CardType.YELLOW }, new ArrayList<ResourceAmount[]>()
	{{
		add(new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 1), new ResourceAmount(Resource.STONE, 1) });
	}}) }, new ResourceAmount[] {}), null),
	MECENATE("BLUE_CARD_2_3", Period.SECOND, 3, new Reward(new Event[] { new EventCard(6, new CardType[] { CardType.BLUE }, new ArrayList<ResourceAmount[]>()
	{{
		add(new ResourceAmount[] { new ResourceAmount(Resource.COIN, 2) });
	}}) }, new ResourceAmount[] {}), null),
	EROE("BLUE_CARD_2_4", Period.SECOND, 4, new Reward(new Event[] { new EventCard(6, new CardType[] { CardType.PURPLE }, new ArrayList<>()) }, new ResourceAmount[] { new ResourceAmount(Resource.COUNCIL_PRIVILEGE, 1) }), null),
	FATTORE("BLUE_CARD_2_5", Period.SECOND, 4, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionWork(3, Work.HARVEST)),
	STUDIOSO("BLUE_CARD_2_6", Period.SECOND, 4, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionWork(3, Work.PRODUCTION)),
	MESSO_PAPALE("BLUE_CARD_2_7", Period.SECOND, 5, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 3) }), null),
	MESSO_REALE("BLUE_CARD_2_8", Period.SECOND, 5, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.COUNCIL_PRIVILEGE, 3) }), null),
	NOBILE("BLUE_CARD_3_1", Period.THIRD, 6, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2, CardType.GREEN) }), null),
	GOVERNATORE("BLUE_CARD_3_2", Period.THIRD, 6, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2, CardType.YELLOW) }), null),
	CORTIGIANA("BLUE_CARD_3_3", Period.THIRD, 7, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2, CardType.BLUE) }), null),
	ARALDO("BLUE_CARD_3_4", Period.THIRD, 6, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 2, CardType.PURPLE) }), null),
	CARDINALE("BLUE_CARD_3_5", Period.THIRD, 4, new Reward(new Event[] { new EventWork(4, Work.HARVEST) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 2) }), null),
	VESCOVO("BLUE_CARD_3_6", Period.THIRD, 5, new Reward(new Event[] { new EventWork(4, Work.PRODUCTION) }, new ResourceAmount[] { new ResourceAmount(Resource.FAITH_POINT, 1) }), null),
	GENERALE("BLUE_CARD_3_7", Period.THIRD, 5, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(Resource.VICTORY_POINT, 1, Resource.MILITARY_POINT, 2) }), null),
	AMBASCIATORE("BLUE_CARD_3_8", Period.THIRD, 6, new Reward(new Event[] { new EventCard(7, new CardType[] { CardType.BLUE, CardType.GREEN, CardType.PURPLE, CardType.YELLOW }, new ArrayList<>()) }, new ResourceAmount[] { new ResourceAmount(Resource.COUNCIL_PRIVILEGE, 1) }), null);
	private String displayName;
	private Period period;
	private int price;
	private Reward instantReward;
	private Object permanentBonus;

	CardBlue(String displayName, Period period, int price, Reward instantReward, Object permanentBonus)
	{
		this.displayName = displayName;
		this.period = period;
		this.price = price;
		this.instantReward = instantReward;
		this.permanentBonus = permanentBonus;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public Period getPeriod()
	{
		return period;
	}

	public int getPrice()
	{
		return price;
	}

	public Reward getInstantReward()
	{
		return instantReward;
	}

	public Object getPermanentBonus()
	{
		return permanentBonus;
	}
}
