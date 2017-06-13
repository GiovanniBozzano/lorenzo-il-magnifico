package it.polimi.ingsw.lim.common.enums;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public enum BoardPosition
{
	NONE,
	HARVEST_SMALL,
	HARVEST_BIG,
	PRODUCTION_SMALL,
	PRODUCTION_BIG,
	MARKET_1,
	MARKET_2,
	MARKET_3,
	MARKET_4,
	TERRITORY_1,
	TERRITORY_2,
	TERRITORY_3,
	TERRITORY_4,
	BUILDING_1,
	BUILDING_2,
	BUILDING_3,
	BUILDING_4,
	CHARACTER_1,
	CHARACTER_2,
	CHARACTER_3,
	CHARACTER_4,
	VENTURE_1,
	VENTURE_2,
	VENTURE_3,
	VENTURE_4,
	COUNCIL_PALACE;
	private static final Map<Row, BoardPosition> DEVELOPMENT_CARDS_BUILDING_POSITIONS = new EnumMap<>(Row.class);
	private static final Map<Row, BoardPosition> DEVELOPMENT_CARDS_CHARACTER_POSITIONS = new EnumMap<>(Row.class);
	private static final Map<Row, BoardPosition> DEVELOPMENT_CARDS_TERRITORY_POSITIONS = new EnumMap<>(Row.class);
	private static final Map<Row, BoardPosition> DEVELOPMENT_CARDS_VENTURE_POSITIONS = new EnumMap<>(Row.class);
	private static final Map<CardType, Map<Row, BoardPosition>> DEVELOPMENT_CARDS_TYPES = new EnumMap<>(CardType.class);
	private static final Map<MarketSlot, BoardPosition> MARKET_POSITIONS = new EnumMap<>(MarketSlot.class);

	static {
		BoardPosition.DEVELOPMENT_CARDS_BUILDING_POSITIONS.put(Row.FIRST, BoardPosition.BUILDING_1);
		BoardPosition.DEVELOPMENT_CARDS_BUILDING_POSITIONS.put(Row.SECOND, BoardPosition.BUILDING_2);
		BoardPosition.DEVELOPMENT_CARDS_BUILDING_POSITIONS.put(Row.THIRD, BoardPosition.BUILDING_3);
		BoardPosition.DEVELOPMENT_CARDS_BUILDING_POSITIONS.put(Row.FOURTH, BoardPosition.BUILDING_4);
		BoardPosition.DEVELOPMENT_CARDS_CHARACTER_POSITIONS.put(Row.FIRST, BoardPosition.CHARACTER_1);
		BoardPosition.DEVELOPMENT_CARDS_CHARACTER_POSITIONS.put(Row.SECOND, BoardPosition.CHARACTER_2);
		BoardPosition.DEVELOPMENT_CARDS_CHARACTER_POSITIONS.put(Row.THIRD, BoardPosition.CHARACTER_3);
		BoardPosition.DEVELOPMENT_CARDS_CHARACTER_POSITIONS.put(Row.FOURTH, BoardPosition.CHARACTER_4);
		BoardPosition.DEVELOPMENT_CARDS_TERRITORY_POSITIONS.put(Row.FIRST, BoardPosition.TERRITORY_1);
		BoardPosition.DEVELOPMENT_CARDS_TERRITORY_POSITIONS.put(Row.SECOND, BoardPosition.TERRITORY_2);
		BoardPosition.DEVELOPMENT_CARDS_TERRITORY_POSITIONS.put(Row.THIRD, BoardPosition.TERRITORY_3);
		BoardPosition.DEVELOPMENT_CARDS_TERRITORY_POSITIONS.put(Row.FOURTH, BoardPosition.TERRITORY_4);
		BoardPosition.DEVELOPMENT_CARDS_VENTURE_POSITIONS.put(Row.FIRST, BoardPosition.VENTURE_1);
		BoardPosition.DEVELOPMENT_CARDS_VENTURE_POSITIONS.put(Row.SECOND, BoardPosition.VENTURE_2);
		BoardPosition.DEVELOPMENT_CARDS_VENTURE_POSITIONS.put(Row.THIRD, BoardPosition.VENTURE_3);
		BoardPosition.DEVELOPMENT_CARDS_VENTURE_POSITIONS.put(Row.FOURTH, BoardPosition.VENTURE_4);
		BoardPosition.DEVELOPMENT_CARDS_TYPES.put(CardType.BUILDING, BoardPosition.DEVELOPMENT_CARDS_BUILDING_POSITIONS);
		BoardPosition.DEVELOPMENT_CARDS_TYPES.put(CardType.CHARACTER, BoardPosition.DEVELOPMENT_CARDS_CHARACTER_POSITIONS);
		BoardPosition.DEVELOPMENT_CARDS_TYPES.put(CardType.TERRITORY, BoardPosition.DEVELOPMENT_CARDS_TERRITORY_POSITIONS);
		BoardPosition.DEVELOPMENT_CARDS_TYPES.put(CardType.VENTURE, BoardPosition.DEVELOPMENT_CARDS_VENTURE_POSITIONS);
		BoardPosition.MARKET_POSITIONS.put(MarketSlot.FIRST, BoardPosition.MARKET_1);
		BoardPosition.MARKET_POSITIONS.put(MarketSlot.SECOND, BoardPosition.MARKET_2);
		BoardPosition.MARKET_POSITIONS.put(MarketSlot.THIRD, BoardPosition.MARKET_3);
		BoardPosition.MARKET_POSITIONS.put(MarketSlot.FOURTH, BoardPosition.MARKET_4);
	}

	public static BoardPosition getDevelopmentCardPosition(CardType cardType, Row row)
	{
		return BoardPosition.DEVELOPMENT_CARDS_TYPES.get(cardType).get(row);
	}

	public static Collection<BoardPosition> getDevelopmentCardsColumnPositions(CardType cardType)
	{
		return BoardPosition.DEVELOPMENT_CARDS_TYPES.get(cardType).values();
	}

	public static Map<MarketSlot, BoardPosition> getMarketPositions()
	{
		return BoardPosition.MARKET_POSITIONS;
	}
}


