package it.polimi.ingsw.lim.common.cards;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.utils.Reward;

public class DevelopmentCardCharacter extends DevelopmentCard
{
	/*
	CONDOTTIERO("BLUE_CARD_1_1", Period.FIRST, 2, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 3) }), new BonusAdditionCard(2, CardType.TERRITORY, new ResourceAmount[][] {})),
	COSTRUTTORE("BLUE_CARD_1_2", Period.FIRST, 4, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionCard(2, CardType.BUILDING, new ResourceAmount[][] { { new ResourceAmount(ResourceType.WOOD, 1) }, { new ResourceAmount(ResourceType.STONE, 1) } })),
	DAMA("BLUE_CARD_1_3", Period.FIRST, 4, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionCard(2, CardType.CHARACTER, new ResourceAmount[][] { { new ResourceAmount(ResourceType.COIN, 1) } })),
	CAVALIERE("BLUE_CARD_1_4", Period.FIRST, 2, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1) }), new BonusAdditionCard(2, CardType.VENTURE, new ResourceAmount[][] {})),
	CONTADINO("BLUE_CARD_1_5", Period.FIRST, 3, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionWork(2, WorkType.HARVEST)),
	ARTIGIANO("BLUE_CARD_1_6", Period.FIRST, 3, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionWork(2, WorkType.PRODUCTION)),
	PREDICATORE("BLUE_CARD_1_7", Period.FIRST, 2, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 4) }), new Malus(new Row[] { Row.THIRD, Row.FOURTH })),
	BADESSA("BLUE_CARD_1_8", Period.FIRST, 3, new Reward(new Event[] { new EventCard(4, new CardType[] { CardType.CHARACTER, CardType.TERRITORY, CardType.VENTURE, CardType.BUILDING }, new ResourceAmount[][] {}) }, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1) }), null),
	CAPITANO("BLUE_CARD_2_1", Period.SECOND, 4, new Reward(new Event[] { new EventCard(6, new CardType[] { CardType.TERRITORY }, new ResourceAmount[][] {}) }, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 2) }), null),
	ARCHITETTO("BLUE_CARD_2_2", Period.SECOND, 4, new Reward(new Event[] { new EventCard(6, new CardType[] { CardType.BUILDING }, new ResourceAmount[][] { { new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1) } }) }, new ResourceAmount[] {}), null),
	MECENATE("BLUE_CARD_2_3", Period.SECOND, 3, new Reward(new Event[] { new EventCard(6, new CardType[] { CardType.CHARACTER }, new ResourceAmount[][] { { new ResourceAmount(ResourceType.COIN, 2) } }) }, new ResourceAmount[] {}), null),
	EROE("BLUE_CARD_2_4", Period.SECOND, 4, new Reward(new Event[] { new EventCard(6, new CardType[] { CardType.VENTURE }, new ResourceAmount[][] {}) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1) }), null),
	FATTORE("BLUE_CARD_2_5", Period.SECOND, 4, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionWork(3, WorkType.HARVEST)),
	STUDIOSO("BLUE_CARD_2_6", Period.SECOND, 4, new Reward(new Event[] {}, new ResourceAmount[] {}), new BonusAdditionWork(3, WorkType.PRODUCTION)),
	MESSO_PAPALE("BLUE_CARD_2_7", Period.SECOND, 5, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 3) }), null),
	MESSO_REALE("BLUE_CARD_2_8", Period.SECOND, 5, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 3) }), null),
	NOBILE("BLUE_CARD_3_1", Period.THIRD, 6, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 2, CardType.TERRITORY) }), null),
	GOVERNATORE("BLUE_CARD_3_2", Period.THIRD, 6, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 2, CardType.BUILDING) }), null),
	CORTIGIANA("BLUE_CARD_3_3", Period.THIRD, 7, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 2, CardType.CHARACTER) }), null),
	ARALDO("BLUE_CARD_3_4", Period.THIRD, 6, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 2, CardType.VENTURE) }), null),
	CARDINALE("BLUE_CARD_3_5", Period.THIRD, 4, new Reward(new Event[] { new EventWork(4, WorkType.HARVEST) }, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 2) }), null),
	VESCOVO("BLUE_CARD_3_6", Period.THIRD, 5, new Reward(new Event[] { new EventWork(4, WorkType.PRODUCTION) }, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1) }), null),
	GENERALE("BLUE_CARD_3_7", Period.THIRD, 5, new Reward(new Event[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 1, ResourceType.MILITARY_POINT, 2) }), null),
	AMBASCIATORE("BLUE_CARD_3_8", Period.THIRD, 6, new Reward(new Event[] { new EventCard(7, new CardType[] { CardType.CHARACTER, CardType.TERRITORY, CardType.VENTURE, CardType.BUILDING }, new ResourceAmount[][] {}) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1) }), null);
	*/
	private final int price;
	private final Reward instantReward;
	private final Object permanentBonus;

	public DevelopmentCardCharacter(String displayName, Period period, int price, Reward instantReward, Object permanentBonus)
	{
		super(displayName, period);
		this.price = price;
		this.instantReward = instantReward;
		this.permanentBonus = permanentBonus;
	}

	public int getPrice()
	{
		return this.price;
	}

	public Reward getInstantReward()
	{
		return this.instantReward;
	}

	public Object getPermanentBonus()
	{
		return this.permanentBonus;
	}
}
